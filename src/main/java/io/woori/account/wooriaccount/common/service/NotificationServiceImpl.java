package io.woori.account.wooriaccount.common.service;

import java.io.IOException;
import java.util.Random;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import io.woori.account.wooriaccount.common.domain.entity.Notification;
import io.woori.account.wooriaccount.common.dto.notification.FindAllNotificationResponseDTO;
import io.woori.account.wooriaccount.common.dto.notification.TxNotifyDTO;
import io.woori.account.wooriaccount.common.exception.CustomException;
import io.woori.account.wooriaccount.common.exception.ErrorCode;
import io.woori.account.wooriaccount.common.repository.EmitterRepository;
import io.woori.account.wooriaccount.common.repository.jpa.NotificationRepository;
import io.woori.account.wooriaccount.common.service.inter.NotificationService;
import io.woori.account.wooriaccount.customer.domain.entity.Customer;
import io.woori.account.wooriaccount.customer.repository.jpa.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
	private static final Long DEFAULT_TIME_OUT = 60L * 1000 * 60; // 1시간
	private static final String NOTIFICATION_NAME = "notification";
    
	private final CustomerRepository customerRepository;
	private final NotificationRepository notificationRepository;
	private final EmitterRepository emitterRepository;

	@Override
	public Page<FindAllNotificationResponseDTO> readNotifications(Pageable pageable, Long customerId) {
		Customer find = customerRepository.findById(customerId).orElseThrow(
			() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER)
		);
		return notificationRepository.findAllByReceiver(pageable, find).map(FindAllNotificationResponseDTO::from);
	}

	@Override
	public SseEmitter subscribe(final Long customerId) {
		final String sseKey = generateSseKey(customerId);
		SseEmitter savedSseEmitter = emitterRepository.save(sseKey, new SseEmitter(DEFAULT_TIME_OUT));

		savedSseEmitter.onCompletion(() -> emitterRepository.deleteById(sseKey));
		savedSseEmitter.onTimeout(() -> emitterRepository.deleteById(sseKey));
		savedSseEmitter.onError((e) -> emitterRepository.deleteById(sseKey));

		send(savedSseEmitter, "", NOTIFICATION_NAME);

		return savedSseEmitter;
	}

	@Override
	public void notify(Long receiverId, Notification notification) {
		log.info("### notify {} ###", receiverId);
		emitterRepository.findAllByCustomerId(receiverId).ifPresent(
			emitterMap -> emitterMap.forEach(
				(key, sseEmitter) -> {
					log.info("SseEmitter - {} : {}", key, sseEmitter);
					emitterRepository.save(key, sseEmitter);
					send(sseEmitter, key, TxNotifyDTO.from(notification));
				}
			)
		);
	}

	private void send(SseEmitter sseEmitter, String sseKey, Object data) {
		try {
			sseEmitter.send(SseEmitter.event()
				.id(sseKey)
				.name(NOTIFICATION_NAME)
				.data(data, MediaType.APPLICATION_JSON));
		} catch (IOException e) {
			emitterRepository.deleteById(sseKey);
			sseEmitter.completeWithError(e);
		}
	}

	private String generateSseKey(Long id) {
		return id + "-" + generateRandomStr();
	}

	private String generateRandomStr() {
		int leftLimit = 97; // letter 'A'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 12;
		String unixTime = String.valueOf(System.currentTimeMillis());
		Random random = new Random();

		String randomStr = random.ints(leftLimit, rightLimit + 1)
			.limit(targetStringLength)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();

		return randomStr + "_" + unixTime;
	}

}

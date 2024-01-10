package io.woori.account.wooriaccount.service;

import io.woori.account.wooriaccount.constant.NotificationType;
import io.woori.account.wooriaccount.domain.NotificationArgs;
import io.woori.account.wooriaccount.domain.entity.Customer;
import io.woori.account.wooriaccount.domain.entity.Notification;
import io.woori.account.wooriaccount.dto.notification.FindAllNotificationResponseDTO;
import io.woori.account.wooriaccount.exception.CustomException;
import io.woori.account.wooriaccount.exception.ErrorCode;
import io.woori.account.wooriaccount.repository.jpa.CustomerRepository;
import io.woori.account.wooriaccount.repository.EmitterRepository;
import io.woori.account.wooriaccount.repository.jpa.NotificationRepository;
import io.woori.account.wooriaccount.service.inter.NotificationService;
import java.util.Map;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

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
                () -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND)
        );

        return notificationRepository.findAllByCustomer(pageable, find).map(FindAllNotificationResponseDTO::from);
    }

    @Override
    public SseEmitter subscribe(final Long id) {
        final String sseKey = generateSseKey(id);
        SseEmitter savedSseEmitter = emitterRepository.save(sseKey, new SseEmitter(DEFAULT_TIME_OUT));

        savedSseEmitter.onCompletion(() -> emitterRepository.deleteById(sseKey));
        savedSseEmitter.onTimeout(() -> emitterRepository.deleteById(sseKey));
        savedSseEmitter.onError((e) -> emitterRepository.deleteById(sseKey));

        send(savedSseEmitter, "", NOTIFICATION_NAME);

        return savedSseEmitter;
    }

    @Override
    public void notify(Customer receiver, String content, NotificationType notificationType, NotificationArgs notificationArgs) {
        Notification notification = Notification.of(receiver, content, notificationType, notificationArgs);

        emitterRepository.findAllByCustomerId(receiver.getCustomerId()).ifPresent(
            emitterMap -> {
                emitterMap.forEach(
                        (key, sseEmitter) -> {
                            emitterRepository.save(key, sseEmitter);
                            send(sseEmitter, key, notification);
                        }
                );
            }
        );
    }

    private void send(SseEmitter sseEmitter, String sseKey, Object data) {
        try {
            sseEmitter.send(SseEmitter.event()
                    .id(sseKey)
                    .name(NOTIFICATION_NAME)
                    .data(data, MediaType.APPLICATION_JSON));
        } catch(IOException exception) {
            emitterRepository.deleteById(sseKey);
            sseEmitter.completeWithError(exception);
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

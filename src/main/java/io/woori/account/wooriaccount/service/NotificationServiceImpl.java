package io.woori.account.wooriaccount.service;

import io.woori.account.wooriaccount.domain.entity.Customer;
import io.woori.account.wooriaccount.dto.notification.FindAllNotificationResponseDTO;
import io.woori.account.wooriaccount.exception.CustomException;
import io.woori.account.wooriaccount.exception.ErrorCode;
import io.woori.account.wooriaccount.repository.jpa.CustomerRepository;
import io.woori.account.wooriaccount.repository.EmitterRepository;
import io.woori.account.wooriaccount.repository.jpa.NotificationRepository;
import io.woori.account.wooriaccount.service.inter.NotificationService;
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
        SseEmitter savedSseEmitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIME_OUT));
        savedSseEmitter.onCompletion(() -> emitterRepository.delete(id));
        savedSseEmitter.onTimeout(() -> emitterRepository.delete(id));
        savedSseEmitter.onError((e) -> emitterRepository.delete(id));

        try {

        }

        return null;
    }

    private void send(SseEmitter sseEmitter, String sseEmitterId, Object data) {
        try {
            sseEmitter.send(SseEmitter.event()
                    .id(sseEmitterId)
                    .name("sse")
                    .data(data, MediaType.APPLICATION_JSON));
        } catch(IOException exception) {
            emitterRepository.deleteEmitterById(emitterId);
            sseEmitter.completeWithError(exception);
        }
    }

}

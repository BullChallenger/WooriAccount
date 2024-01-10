package io.woori.account.wooriaccount.service.inter;

import io.woori.account.wooriaccount.dto.notification.FindAllNotificationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {

    Page<FindAllNotificationResponseDTO> readNotifications(Pageable pageable, Long customerId);

    SseEmitter connectNotification(Long id);

}

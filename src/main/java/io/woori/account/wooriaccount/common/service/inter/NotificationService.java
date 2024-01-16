package io.woori.account.wooriaccount.common.service.inter;

import io.woori.account.wooriaccount.common.domain.entity.Notification;
import io.woori.account.wooriaccount.common.dto.notification.FindAllNotificationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {

    Page<FindAllNotificationResponseDTO> readNotifications(Pageable pageable, Long customerId);

    SseEmitter subscribe(Long customerId);

    void notify(Long receiverId, Notification notification);
}

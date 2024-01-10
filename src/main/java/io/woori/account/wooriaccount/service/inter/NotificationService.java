package io.woori.account.wooriaccount.service.inter;

import io.woori.account.wooriaccount.constant.NotificationType;
import io.woori.account.wooriaccount.domain.NotificationArgs;
import io.woori.account.wooriaccount.domain.entity.Customer;
import io.woori.account.wooriaccount.dto.notification.FindAllNotificationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {

    Page<FindAllNotificationResponseDTO> readNotifications(Pageable pageable, Long customerId);

    SseEmitter subscribe(Long id);

    void notify(Customer receiver, String content, NotificationType notificationType, NotificationArgs notificationArgs);
}

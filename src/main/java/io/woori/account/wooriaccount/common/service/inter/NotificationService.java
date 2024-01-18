package io.woori.account.wooriaccount.common.service.inter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import io.woori.account.wooriaccount.common.domain.entity.Notification;
import io.woori.account.wooriaccount.common.dto.notification.FindAllNotificationResponseDTO;

public interface NotificationService {

	Page<FindAllNotificationResponseDTO> readNotifications(Pageable pageable, Long customerId);

	SseEmitter subscribe(Long customerId);

	void notify(Long receiverId, Notification notification);
}

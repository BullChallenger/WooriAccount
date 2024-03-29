package io.woori.account.wooriaccount.common.dto.notification;

import java.time.LocalDateTime;

import io.woori.account.wooriaccount.common.constant.NotificationType;
import io.woori.account.wooriaccount.common.domain.NotificationArgs;
import io.woori.account.wooriaccount.common.domain.entity.Notification;
import io.woori.account.wooriaccount.customer.domain.entity.Customer;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FindAllNotificationResponseDTO {

	private final Long notificationId;

	private final Customer customer;

	private final String notificationContent;

	private final NotificationType notificationType;

	private final NotificationArgs notificationArgs;

	private final LocalDateTime createdAt;

	private final LocalDateTime updatedAt;

	@Builder
	public FindAllNotificationResponseDTO(Long notificationId, Customer customer, String notificationContent,
		NotificationType notificationType,
		NotificationArgs notificationArgs, LocalDateTime createdAt,
		LocalDateTime updatedAt) {
		this.notificationId = notificationId;
		this.customer = customer;
		this.notificationContent = notificationContent;
		this.notificationType = notificationType;
		this.notificationArgs = notificationArgs;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static FindAllNotificationResponseDTO from(Notification notification) {
		return FindAllNotificationResponseDTO.builder()
			.notificationId(notification.getId())
			.customer(notification.getReceiver())
			.notificationContent(notification.getNotificationType().getNotificationContent())
			.notificationType(notification.getNotificationType())
			.notificationArgs(notification.getNotificationArgs())
			.createdAt(notification.getCreatedTime())
			.updatedAt(notification.getLastModifiedTime())
			.build();
	}

}

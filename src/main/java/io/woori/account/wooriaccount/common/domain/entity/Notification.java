package io.woori.account.wooriaccount.common.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import io.woori.account.wooriaccount.common.constant.NotificationType;
import io.woori.account.wooriaccount.common.domain.NotificationArgs;
import io.woori.account.wooriaccount.customer.domain.entity.Customer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Table(name = "notifications")
@Where(clause = "IS_DELETED = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@TypeDef(name = "json", typeClass = JsonType.class)
public class Notification extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id")
	private Customer receiver;

	private String content;

	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;

	@Type(type = "json")
	@Column(columnDefinition = "json")
	private NotificationArgs notificationArgs;

	@Builder
	public Notification(Customer receiver, String content, NotificationType notificationType,
		NotificationArgs notificationArgs) {
		this.receiver = receiver;
		this.content = content;
		this.notificationType = notificationType;
		this.notificationArgs = notificationArgs;
	}

	public static Notification of(Customer receiver, String content, NotificationType notificationType,
		NotificationArgs notificationArgs) {
		return Notification.builder()
			.receiver(receiver)
			.content(content)
			.notificationType(notificationType)
			.notificationArgs(notificationArgs)
			.build();
	}

}

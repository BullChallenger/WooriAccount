package io.woori.account.wooriaccount.domain.entity;

import io.woori.account.wooriaccount.constant.NotificationType;
import io.woori.account.wooriaccount.domain.NotificationArgs;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Table(name = "notifications")
@Where(clause = "IS_DELETED = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer receiver;

    private String content;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private NotificationArgs notificationArgs;

    @Builder
    public Notification(Customer receiver, String content, NotificationType notificationType, NotificationArgs notificationArgs) {
        this.receiver = receiver;
        this.content = content;
        this.notificationType = notificationType;
        this.notificationArgs = notificationArgs;
    }

    public static Notification of(Customer receiver, String content, NotificationType notificationType, NotificationArgs notificationArgs) {
        return Notification.builder()
                            .receiver(receiver)
                            .content(content)
                            .notificationType(notificationType)
                            .notificationArgs(notificationArgs)
                            .build();
    }

}

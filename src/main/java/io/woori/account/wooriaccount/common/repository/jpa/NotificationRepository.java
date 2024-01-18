package io.woori.account.wooriaccount.common.repository.jpa;

import org.springframework.data.repository.Repository;

import io.woori.account.wooriaccount.common.domain.entity.Notification;
import io.woori.account.wooriaccount.common.repository.common.CommonNotificationRepository;

public interface NotificationRepository extends Repository<Notification, Long>, CommonNotificationRepository {

}

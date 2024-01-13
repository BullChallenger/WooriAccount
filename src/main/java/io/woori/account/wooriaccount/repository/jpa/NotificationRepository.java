package io.woori.account.wooriaccount.repository.jpa;

import io.woori.account.wooriaccount.domain.entity.Notification;
import io.woori.account.wooriaccount.repository.common.CommonNotificationRepository;
import org.springframework.data.repository.Repository;

public interface NotificationRepository extends Repository<Notification, Long>,CommonNotificationRepository {

}

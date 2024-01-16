package io.woori.account.wooriaccount.common.repository.jpa;

import io.woori.account.wooriaccount.common.repository.common.CommonNotificationRepository;
import io.woori.account.wooriaccount.common.domain.entity.Notification;
import org.springframework.data.repository.Repository;

public interface NotificationRepository extends Repository<Notification, Long>, CommonNotificationRepository {

}

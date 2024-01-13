package io.woori.account.wooriaccount.repository.common;

import io.woori.account.wooriaccount.domain.entity.Customer;
import io.woori.account.wooriaccount.domain.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonNotificationRepository {

    Page<Notification> findAllByReceiver(Pageable pageable, Customer receiver);

    Notification save(Notification notification);

}

package io.woori.account.wooriaccount.common.repository.common;

import io.woori.account.wooriaccount.customer.domain.entity.Customer;
import io.woori.account.wooriaccount.common.domain.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonNotificationRepository {

    Page<Notification> findAllByReceiver(Pageable pageable, Customer receiver);

    Notification save(Notification notification);

}

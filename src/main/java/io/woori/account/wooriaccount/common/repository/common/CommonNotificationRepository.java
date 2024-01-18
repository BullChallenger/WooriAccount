package io.woori.account.wooriaccount.common.repository.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.woori.account.wooriaccount.common.domain.entity.Notification;
import io.woori.account.wooriaccount.customer.domain.entity.Customer;

public interface CommonNotificationRepository {
	Page<Notification> findAllByReceiver(Pageable pageable, Customer receiver);

	Notification save(Notification notification);

}

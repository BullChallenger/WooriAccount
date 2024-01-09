package io.woori.account.wooriaccount.service.inter;

import io.woori.account.wooriaccount.dto.notification.FindAllNotificationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    Page<FindAllNotificationResponseDTO> readNotifications(Pageable pageable, Long customerId);

}

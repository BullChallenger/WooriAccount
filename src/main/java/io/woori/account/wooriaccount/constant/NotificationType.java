package io.woori.account.wooriaccount.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

    DEPOSIT_TX_OCCUR("원이 입금되었습니다."),
    WITHDRAW_TX_OCCUR("원이 출금되었습니다.")
    ;

    private final String notificationContent;

}

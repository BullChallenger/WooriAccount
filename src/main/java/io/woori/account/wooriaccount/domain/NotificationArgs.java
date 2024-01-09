package io.woori.account.wooriaccount.domain;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NotificationArgs {

    private final Long fromId;

    private final List<Long> receiverId;

    @Builder
    public NotificationArgs(Long fromCustomerId, List<Long> senderId) {
        this.fromId = fromCustomerId;
        this.receiverId = senderId;
    }

}

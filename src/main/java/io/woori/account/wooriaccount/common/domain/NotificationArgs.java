package io.woori.account.wooriaccount.common.domain;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationArgs {

	private Long fromAccountId;
	private List<Long> receiverAccountId;

	@Builder
	public NotificationArgs(Long fromCustomerId, List<Long> receiverAccountId) {
		this.fromAccountId = fromCustomerId;
		this.receiverAccountId = receiverAccountId;
	}

	public static NotificationArgs of(Long fromAccountId, List<Long> receiverAccountId) {
		return NotificationArgs.builder()
			.fromCustomerId(fromAccountId)
			.receiverAccountId(receiverAccountId)
			.build();
	}

}

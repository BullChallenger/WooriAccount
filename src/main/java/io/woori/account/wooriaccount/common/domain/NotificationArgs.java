package io.woori.account.wooriaccount.common.domain;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationArgs {

	private Long fromCustomerId;
	private List<Long> receiverCustomerId;

	@Builder
	public NotificationArgs(Long fromCustomerId, List<Long> receiverCustomerId) {
		this.fromCustomerId = fromCustomerId;
		this.receiverCustomerId = receiverCustomerId;
	}

	public static NotificationArgs of(Long fromAccountId, List<Long> receiverCustomerId) {
		return NotificationArgs.builder()
			.fromCustomerId(fromAccountId)
			.receiverCustomerId(receiverCustomerId)
			.build();
	}

}

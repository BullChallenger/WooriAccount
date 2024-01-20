package io.woori.account.wooriaccount.txhistory.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import io.woori.account.wooriaccount.txhistory.domain.WithdrawTxHistory;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FindAllWithdrawTxResponseDTO {

	private final String receiverName;

	private final BigDecimal amount;

	private final BigDecimal balanceAfterTx;

	private final String description;

	private final LocalDateTime createdAt;

	@Builder
	@QueryProjection
	public FindAllWithdrawTxResponseDTO(
		String receiverName,
		BigDecimal amount,
		BigDecimal balanceAfterTx,
		String description,
		LocalDateTime createdAt
	) {
		this.receiverName = receiverName;
		this.amount = amount;
		this.balanceAfterTx = balanceAfterTx;
		this.description = description;
		this.createdAt = createdAt;
	}

	public static FindAllWithdrawTxResponseDTO from(WithdrawTxHistory entity) {
		return FindAllWithdrawTxResponseDTO.builder()
			.receiverName(entity.getReceiver().getCustomer().getCustomerName())
			.amount(entity.getAmount())
			.balanceAfterTx(entity.getBalanceAfterTx())
			.description(entity.getDescription())
			.createdAt(entity.getCreatedTime())
			.build();
	}

}

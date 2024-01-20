package io.woori.account.wooriaccount.txhistory.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import io.woori.account.wooriaccount.txhistory.domain.DepositTxHistory;
import io.woori.account.wooriaccount.txhistory.domain.WithdrawTxHistory;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FindTxResponseDTO implements Comparable<FindTxResponseDTO> {

	private final String targetName;

	private final BigDecimal amount;

	private final BigDecimal balanceAfterTx;

	private final String description;

	private final LocalDateTime createdAt;

	@Builder
	@QueryProjection
	public FindTxResponseDTO(
		String targetName,
		BigDecimal amount,
		BigDecimal balanceAfterTx,
		String description,
		LocalDateTime createdAt
	) {
		this.targetName = targetName;
		this.amount = amount;
		this.balanceAfterTx = balanceAfterTx;
		this.description = description;
		this.createdAt = createdAt;
	}

	public static FindTxResponseDTO from(DepositTxHistory depositTxHistory) {
		return FindTxResponseDTO.builder()
			.targetName(depositTxHistory.getReceiver().getCustomer().getCustomerName())
			.amount(depositTxHistory.getAmount())
			.balanceAfterTx(depositTxHistory.getBalanceAfterTx())
			.description(depositTxHistory.getDescription())
			.createdAt(depositTxHistory.getCreatedTime())
			.build();
	}

	public static FindTxResponseDTO from(WithdrawTxHistory txHistory) {
		return FindTxResponseDTO.builder()
			.targetName(txHistory.getReceiver().getCustomer().getCustomerName())
			.amount(txHistory.getAmount())
			.balanceAfterTx(txHistory.getBalanceAfterTx())
			.description(txHistory.getDescription())
			.createdAt(txHistory.getCreatedTime())
			.build();
	}

	@Override
	public int compareTo(FindTxResponseDTO o) {
		return this.createdAt.compareTo(o.getCreatedAt());
	}
}

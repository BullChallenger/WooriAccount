package io.woori.account.wooriaccount.txhistory.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import io.woori.account.wooriaccount.account.domain.entity.Account;
import io.woori.account.wooriaccount.common.domain.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "IS_DELETED = false")
public class WithdrawTxHistory extends BaseEntity {

	@Id
	@Column(name = "tx_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long txId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_account_id")
	private Account sender;

	@ManyToOne
	@JoinColumn(name = "receiver_account_id")
	private Account receiver;

	@Column(nullable = false)
	private BigDecimal amount;

	@Column(nullable = false)
	private BigDecimal balanceAfterTx;

	private String description;

	@Builder
	public WithdrawTxHistory(Account sender, Account receiver, BigDecimal amount, BigDecimal balanceAfterTx,
		String description) {
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
		this.balanceAfterTx = balanceAfterTx;
		this.description = description;
	}

	public static WithdrawTxHistory of(Account sender,
		Account receiver,
		BigDecimal amount,
		BigDecimal balanceAfterTx,
		String description
	) {
		return WithdrawTxHistory.builder()
			.sender(sender)
			.receiver(receiver)
			.amount(amount)
			.balanceAfterTx(balanceAfterTx)
			.description(description)
			.build();
	}

}

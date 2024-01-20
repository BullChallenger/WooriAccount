package io.woori.account.wooriaccount.account.domain.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import io.woori.account.wooriaccount.common.domain.entity.BaseEntity;
import io.woori.account.wooriaccount.customer.domain.entity.Customer;
import io.woori.account.wooriaccount.txhistory.domain.DepositTxHistory;
import io.woori.account.wooriaccount.txhistory.domain.WithdrawTxHistory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * 계좌와 관련된 엔티티 클래스 입니다.
 * @author : yeom hwiju
 * */
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Table(name = "accounts")
@Where(clause = "IS_DELETED = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId; // 계좌 id

	@Column(nullable = false, unique = true)
	private String accountNumber; // 계좌 번호

	private BigDecimal accountBalance; // 계좌 잔고

	private BigDecimal accountLimit; // 계좌 한도

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer; // 계좌 주인

	@OneToMany(mappedBy = "sender")
	private List<WithdrawTxHistory> withdrawTxHistories = new ArrayList<>(); // 출금 기록

	@OneToMany(mappedBy = "receiver")
	private List<DepositTxHistory> depositTxHistories = new ArrayList<>(); // 입금 기록

	@Builder
	public Account(String accountNumber, BigDecimal accountBalance, BigDecimal accountLimit, Customer customer) {
		this.accountNumber = accountNumber;
		this.accountBalance = accountBalance;
		this.accountLimit = accountLimit;
		this.customer = customer;

	}

	public static Account createAccount(String accountNumber, BigDecimal accountBalance, BigDecimal accountLimit,
		Customer customer) {
		return Account.builder()
			.accountNumber(accountNumber)
			.accountBalance(accountBalance)
			.accountLimit(accountLimit)
			.customer(customer)
			.build();
	}

	public Account deletedAccount(String accountNumber) {
		this.accountNumber = "deleted " + accountNumber;
		setIsDeleted(true);

		return this;
	}

}

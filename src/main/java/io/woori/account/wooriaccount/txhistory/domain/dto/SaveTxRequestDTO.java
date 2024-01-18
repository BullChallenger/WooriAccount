package io.woori.account.wooriaccount.txhistory.domain.dto;

import java.math.BigDecimal;

import io.woori.account.wooriaccount.account.domain.entity.Account;
import lombok.Getter;

@Getter
public class SaveTxRequestDTO {

	private Account sender;

	private Account receiver;

	private BigDecimal amount;

	private BigDecimal balanceAfterTx;

	private String description;

}
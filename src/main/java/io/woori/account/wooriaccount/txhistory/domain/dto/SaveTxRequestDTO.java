package io.woori.account.wooriaccount.txhistory.domain.dto;

import io.woori.account.wooriaccount.account.domain.entity.Account;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class SaveTxRequestDTO {

    private Account sender;

    private Account receiver;

    private BigDecimal amount;

    private BigDecimal balanceAfterTx;

    private String description;

}

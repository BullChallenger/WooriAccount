package io.woori.account.wooriaccount.dto.tx;

import io.woori.account.wooriaccount.domain.entity.Account;
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

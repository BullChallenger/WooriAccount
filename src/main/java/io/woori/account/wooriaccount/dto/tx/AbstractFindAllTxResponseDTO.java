package io.woori.account.wooriaccount.dto.tx;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class AbstractFindAllTxResponseDTO {

    private final BigDecimal amount;

    private final BigDecimal balanceAfterTx;

    private final String description;

    private final LocalDateTime createdAt;

    public AbstractFindAllTxResponseDTO(BigDecimal amount,
                                        BigDecimal balanceAfterTx,
                                        String description,
                                        LocalDateTime createdAt)
    {
        this.amount = amount;
        this.balanceAfterTx = balanceAfterTx;
        this.description = description;
        this.createdAt = createdAt;
    }

}

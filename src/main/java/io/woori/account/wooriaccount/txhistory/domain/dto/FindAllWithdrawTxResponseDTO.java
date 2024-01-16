package io.woori.account.wooriaccount.txhistory.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class FindAllWithdrawTxResponseDTO {

    private final String receiverName;

    private final BigDecimal amount;

    private final BigDecimal balanceAfterTx;

    private final String description;

    private final LocalDateTime createdAt;

    @QueryProjection
    public FindAllWithdrawTxResponseDTO(String receiverName,
                                        BigDecimal amount,
                                        BigDecimal balanceAfterTx,
                                        String description,
                                        LocalDateTime createdAt) {
        this.receiverName = receiverName;
        this.amount = amount;
        this.balanceAfterTx = balanceAfterTx;
        this.description = description;
        this.createdAt = createdAt;
    }

}

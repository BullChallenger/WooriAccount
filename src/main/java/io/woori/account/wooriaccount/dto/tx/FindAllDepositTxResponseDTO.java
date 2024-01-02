package io.woori.account.wooriaccount.dto.tx;

import com.querydsl.core.annotations.QueryProjection;
import io.woori.account.wooriaccount.domain.entity.DepositTxHistory;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class FindAllDepositTxResponseDTO {

    private final String senderName;

    private final BigDecimal amount;

    private final BigDecimal balanceAfterTx;

    private final String description;

    private final LocalDateTime createdAt;

    @QueryProjection
    public FindAllDepositTxResponseDTO(String senderName,
                                       BigDecimal amount,
                                       BigDecimal balanceAfterTx,
                                       String description,
                                       LocalDateTime createdAt) {
        this.senderName = senderName;
        this.amount = amount;
        this.balanceAfterTx = balanceAfterTx;
        this.description = description;
        this.createdAt = createdAt;
    }
}

package io.woori.account.wooriaccount.dto.tx;

import com.querydsl.core.annotations.QueryProjection;
import io.woori.account.wooriaccount.domain.entity.DepositTxHistory;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class FindAllDepositTxResponseDTO extends AbstractFindAllTxResponseDTO {

    private final String senderName;

    @QueryProjection
    public FindAllDepositTxResponseDTO(String senderName,
                                       BigDecimal amount,
                                       BigDecimal balanceAfterTx,
                                       String description,
                                       LocalDateTime createdAt)
    {
        super(amount, balanceAfterTx, description, createdAt);
        this.senderName = senderName;
    }

}

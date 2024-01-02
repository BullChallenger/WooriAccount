package io.woori.account.wooriaccount.dto.tx;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * io.woori.account.wooriaccount.dto.tx.QFindAllDepositTxResponseDTO is a Querydsl Projection type for FindAllDepositTxResponseDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QFindAllDepositTxResponseDTO extends ConstructorExpression<FindAllDepositTxResponseDTO> {

    private static final long serialVersionUID = 972139247L;

    public QFindAllDepositTxResponseDTO(com.querydsl.core.types.Expression<String> senderName, com.querydsl.core.types.Expression<? extends java.math.BigDecimal> amount, com.querydsl.core.types.Expression<? extends java.math.BigDecimal> balanceAfterTx, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt) {
        super(FindAllDepositTxResponseDTO.class, new Class<?>[]{String.class, java.math.BigDecimal.class, java.math.BigDecimal.class, String.class, java.time.LocalDateTime.class}, senderName, amount, balanceAfterTx, description, createdAt);
    }

}


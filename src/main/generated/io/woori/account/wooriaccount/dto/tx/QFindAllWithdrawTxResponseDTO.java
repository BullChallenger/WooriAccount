package io.woori.account.wooriaccount.dto.tx;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * io.woori.account.wooriaccount.dto.tx.QFindAllWithdrawTxResponseDTO is a Querydsl Projection type for FindAllWithdrawTxResponseDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QFindAllWithdrawTxResponseDTO extends ConstructorExpression<FindAllWithdrawTxResponseDTO> {

    private static final long serialVersionUID = 1440533501L;

    public QFindAllWithdrawTxResponseDTO(com.querydsl.core.types.Expression<String> receiverName, com.querydsl.core.types.Expression<? extends java.math.BigDecimal> amount, com.querydsl.core.types.Expression<? extends java.math.BigDecimal> balanceAfterTx, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt) {
        super(FindAllWithdrawTxResponseDTO.class, new Class<?>[]{String.class, java.math.BigDecimal.class, java.math.BigDecimal.class, String.class, java.time.LocalDateTime.class}, receiverName, amount, balanceAfterTx, description, createdAt);
    }

}


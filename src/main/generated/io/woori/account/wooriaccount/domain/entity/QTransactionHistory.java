package io.woori.account.wooriaccount.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTransactionHistory is a Querydsl query type for TransactionHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTransactionHistory extends EntityPathBase<TransactionHistory> {

    private static final long serialVersionUID = -2046899651L;

    public static final QTransactionHistory transactionHistory = new QTransactionHistory("transactionHistory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QTransactionHistory(String variable) {
        super(TransactionHistory.class, forVariable(variable));
    }

    public QTransactionHistory(Path<? extends TransactionHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTransactionHistory(PathMetadata metadata) {
        super(TransactionHistory.class, metadata);
    }

}


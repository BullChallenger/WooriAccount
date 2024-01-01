package io.woori.account.wooriaccount.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = -641003994L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAccount account = new QAccount("account");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final NumberPath<Integer> accountBalance = createNumber("accountBalance", Integer.class);

    public final NumberPath<Long> accountId = createNumber("accountId", Long.class);

    public final NumberPath<Integer> accountLimit = createNumber("accountLimit", Integer.class);

    public final StringPath accountNumber = createString("accountNumber");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final QCustomer customer;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedTime = _super.lastModifiedTime;

    public final ListPath<TransactionHistory, QTransactionHistory> transactionHistories = this.<TransactionHistory, QTransactionHistory>createList("transactionHistories", TransactionHistory.class, QTransactionHistory.class, PathInits.DIRECT2);

    public QAccount(String variable) {
        this(Account.class, forVariable(variable), INITS);
    }

    public QAccount(Path<? extends Account> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAccount(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAccount(PathMetadata metadata, PathInits inits) {
        this(Account.class, metadata, inits);
    }

    public QAccount(Class<? extends Account> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new QCustomer(forProperty("customer")) : null;
    }

}


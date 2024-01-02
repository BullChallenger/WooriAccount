package io.woori.account.wooriaccount.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWithdrawTxHistory is a Querydsl query type for WithdrawTxHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWithdrawTxHistory extends EntityPathBase<WithdrawTxHistory> {

    private static final long serialVersionUID = -1308536257L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWithdrawTxHistory withdrawTxHistory = new QWithdrawTxHistory("withdrawTxHistory");

    public final QAbstractTxHistory _super;

    //inherited
    public final NumberPath<java.math.BigDecimal> amount;

    //inherited
    public final NumberPath<java.math.BigDecimal> balanceAfterTx;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime;

    //inherited
    public final StringPath description;

    //inherited
    public final BooleanPath isDeleted;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedTime;

    // inherited
    public final QAccount receiver;

    // inherited
    public final QAccount sender;

    //inherited
    public final NumberPath<Long> txId;

    public QWithdrawTxHistory(String variable) {
        this(WithdrawTxHistory.class, forVariable(variable), INITS);
    }

    public QWithdrawTxHistory(Path<? extends WithdrawTxHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWithdrawTxHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWithdrawTxHistory(PathMetadata metadata, PathInits inits) {
        this(WithdrawTxHistory.class, metadata, inits);
    }

    public QWithdrawTxHistory(Class<? extends WithdrawTxHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QAbstractTxHistory(type, metadata, inits);
        this.amount = _super.amount;
        this.balanceAfterTx = _super.balanceAfterTx;
        this.createdTime = _super.createdTime;
        this.description = _super.description;
        this.isDeleted = _super.isDeleted;
        this.lastModifiedTime = _super.lastModifiedTime;
        this.receiver = _super.receiver;
        this.sender = _super.sender;
        this.txId = _super.txId;
    }

}


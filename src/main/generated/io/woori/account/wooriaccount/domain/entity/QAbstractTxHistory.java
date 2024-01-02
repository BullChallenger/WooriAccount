package io.woori.account.wooriaccount.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAbstractTxHistory is a Querydsl query type for AbstractTxHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAbstractTxHistory extends EntityPathBase<AbstractTxHistory> {

    private static final long serialVersionUID = -1438603225L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAbstractTxHistory abstractTxHistory = new QAbstractTxHistory("abstractTxHistory");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> balanceAfterTx = createNumber("balanceAfterTx", java.math.BigDecimal.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final StringPath description = createString("description");

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedTime = _super.lastModifiedTime;

    public final QAccount receiver;

    public final QAccount sender;

    public final NumberPath<Long> txId = createNumber("txId", Long.class);

    public QAbstractTxHistory(String variable) {
        this(AbstractTxHistory.class, forVariable(variable), INITS);
    }

    public QAbstractTxHistory(Path<? extends AbstractTxHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAbstractTxHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAbstractTxHistory(PathMetadata metadata, PathInits inits) {
        this(AbstractTxHistory.class, metadata, inits);
    }

    public QAbstractTxHistory(Class<? extends AbstractTxHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.receiver = inits.isInitialized("receiver") ? new QAccount(forProperty("receiver"), inits.get("receiver")) : null;
        this.sender = inits.isInitialized("sender") ? new QAccount(forProperty("sender"), inits.get("sender")) : null;
    }

}


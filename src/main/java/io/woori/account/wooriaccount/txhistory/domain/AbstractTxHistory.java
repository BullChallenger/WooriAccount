package io.woori.account.wooriaccount.txhistory.domain;


import io.woori.account.wooriaccount.account.domain.entity.Account;
import io.woori.account.wooriaccount.common.domain.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Where(clause = "IS_DELETED = false")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractTxHistory extends BaseEntity {

    @Id
    @Column(name = "tx_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long txId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_account_id")
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "receiver_account_id")
    private Account receiver;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal balanceAfterTx;

    private String description;

    public AbstractTxHistory(Account sender, Account receiver, BigDecimal amount, BigDecimal balanceAfterTx, String description) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.balanceAfterTx = balanceAfterTx;
        this.description = description;
    }

}

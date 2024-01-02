package io.woori.account.wooriaccount.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(
        name = "txId",
        column = @Column(name = "deposit_tx_id")
)
@DiscriminatorValue(value = "deposit_tx")
public class DepositTxHistory extends AbstractTxHistory {

    @Builder
    public DepositTxHistory(Account sender,
                            Account receiver,
                            BigDecimal amount,
                            BigDecimal balanceAfterTx,
                            String description
    ) {
        super(sender, receiver, amount, balanceAfterTx, description);
    }

    public static DepositTxHistory of(Account sender,
                                      Account receiver,
                                      BigDecimal amount,
                                      BigDecimal balanceAfterTx,
                                      String description
    ) {
        return DepositTxHistory.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(amount)
                .balanceAfterTx(balanceAfterTx)
                .description(description)
                .build();
    }

}

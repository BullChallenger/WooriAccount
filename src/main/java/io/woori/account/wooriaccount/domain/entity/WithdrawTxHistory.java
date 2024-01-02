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
        column = @Column(name = "withdraw_tx_id")
)
@DiscriminatorValue(value = "withdraw_tx")
public class WithdrawTxHistory extends AbstractTxHistory {

    @Builder
    public WithdrawTxHistory(Account sender,
                            Account receiver,
                            BigDecimal amount,
                            BigDecimal balanceAfterTx,
                            String description
    ) {
        super(sender, receiver, amount, balanceAfterTx, description);
    }

    public static WithdrawTxHistory of(Account sender,
                                      Account receiver,
                                      BigDecimal amount,
                                      BigDecimal balanceAfterTx,
                                      String description
    ) {
        return WithdrawTxHistory.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(amount)
                .balanceAfterTx(balanceAfterTx)
                .description(description)
                .build();
    }

}

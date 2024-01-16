package io.woori.account.wooriaccount.txhistory.domain.dto;

import io.woori.account.wooriaccount.txhistory.domain.AbstractTxHistory;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class FindAllTxResponseDTO implements Comparable<FindAllTxResponseDTO> {

    private final String senderName;

    private final String receiverName;

    private final BigDecimal amount;

    private final BigDecimal balanceAfterTx;

    private final String description;

    private final LocalDateTime createdAt;

    @Builder
    public FindAllTxResponseDTO(String senderName,
                                String receiverName,
                                BigDecimal amount,
                                BigDecimal balanceAfterTx,
                                String description,
                                LocalDateTime createdAt) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.amount = amount;
        this.balanceAfterTx = balanceAfterTx;
        this.description = description;
        this.createdAt = createdAt;
    }

    public static FindAllTxResponseDTO of(AbstractTxHistory txHistory) {
        return FindAllTxResponseDTO.builder()
                .senderName(txHistory.getSender().getCustomer().getCustomerName())
                .receiverName(txHistory.getReceiver().getCustomer().getCustomerName())
                .amount(txHistory.getAmount())
                .balanceAfterTx(txHistory.getBalanceAfterTx())
                .description(txHistory.getDescription())
                .createdAt(txHistory.getCreatedTime())
                .build();
    }

    @Override
    public int compareTo(FindAllTxResponseDTO o) {
        return this.createdAt.compareTo(o.getCreatedAt());
    }
}

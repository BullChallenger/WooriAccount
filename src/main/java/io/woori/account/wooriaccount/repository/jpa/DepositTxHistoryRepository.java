package io.woori.account.wooriaccount.repository.jpa;

import io.woori.account.wooriaccount.domain.entity.DepositTxHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepositTxHistoryRepository extends JpaRepository<DepositTxHistory, Long> {
    List<DepositTxHistory> findByReceiverAccountId(Long receiverId);

}

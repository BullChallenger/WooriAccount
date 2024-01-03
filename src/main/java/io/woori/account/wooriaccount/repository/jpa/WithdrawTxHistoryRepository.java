package io.woori.account.wooriaccount.repository.jpa;

import io.woori.account.wooriaccount.domain.entity.DepositTxHistory;
import io.woori.account.wooriaccount.domain.entity.WithdrawTxHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithdrawTxHistoryRepository extends JpaRepository<WithdrawTxHistory, Long> {
    List<WithdrawTxHistory> findBySenderAccountId (Long receiverId);

}

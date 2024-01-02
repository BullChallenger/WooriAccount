package io.woori.account.wooriaccount.repository.jpa;

import io.woori.account.wooriaccount.domain.entity.AbstractTxHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositTxHistoryRepository extends JpaRepository<AbstractTxHistory, Long> {

}

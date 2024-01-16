package io.woori.account.wooriaccount.txhistory.repository.jpa;

import io.woori.account.wooriaccount.txhistory.domain.DepositTxHistory;
import io.woori.account.wooriaccount.txhistory.repository.basic.CommonTxHistoryRepository;
import org.springframework.data.repository.Repository;

public interface DepositTxHistoryRepository extends Repository<DepositTxHistory, Long>, CommonTxHistoryRepository<DepositTxHistory> {
}

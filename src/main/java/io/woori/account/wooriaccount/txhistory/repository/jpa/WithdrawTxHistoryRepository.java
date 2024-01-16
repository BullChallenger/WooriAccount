package io.woori.account.wooriaccount.txhistory.repository.jpa;

import io.woori.account.wooriaccount.txhistory.domain.WithdrawTxHistory;
import io.woori.account.wooriaccount.txhistory.repository.basic.CommonTxHistoryRepository;
import org.springframework.data.repository.Repository;

public interface WithdrawTxHistoryRepository extends Repository<WithdrawTxHistory, Long>, CommonTxHistoryRepository<WithdrawTxHistory> {
}
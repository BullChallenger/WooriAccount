package io.woori.account.wooriaccount.txhistory.repository.jpa;

import org.springframework.data.repository.Repository;

import io.woori.account.wooriaccount.txhistory.domain.WithdrawTxHistory;
import io.woori.account.wooriaccount.txhistory.repository.basic.CommonTxHistoryRepository;

public interface WithdrawTxHistoryRepository
	extends Repository<WithdrawTxHistory, Long>, CommonTxHistoryRepository<WithdrawTxHistory> {

}

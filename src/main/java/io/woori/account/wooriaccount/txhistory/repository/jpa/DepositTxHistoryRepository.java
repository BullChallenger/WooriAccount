package io.woori.account.wooriaccount.txhistory.repository.jpa;

import org.springframework.data.repository.Repository;

import io.woori.account.wooriaccount.txhistory.domain.DepositTxHistory;
import io.woori.account.wooriaccount.txhistory.repository.basic.CommonTxHistoryRepository;

public interface DepositTxHistoryRepository
	extends Repository<DepositTxHistory, Long>, CommonTxHistoryRepository<DepositTxHistory> {

}

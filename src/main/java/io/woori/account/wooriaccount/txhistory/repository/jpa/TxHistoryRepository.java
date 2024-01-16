package io.woori.account.wooriaccount.txhistory.repository.jpa;




import org.springframework.data.repository.Repository;

import io.woori.account.wooriaccount.txhistory.domain.AbstractTxHistory;
import io.woori.account.wooriaccount.txhistory.repository.basic.CommonTxHistoryRepository;



public interface TxHistoryRepository<Tx> extends Repository<AbstractTxHistory, Long>, CommonTxHistoryRepository<Tx> {

}

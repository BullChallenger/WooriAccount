package io.woori.account.wooriaccount.repository.jpa;




import org.springframework.data.repository.Repository;

import io.woori.account.wooriaccount.domain.entity.AbstractTxHistory;



public interface TxHistoryRepository extends Repository<AbstractTxHistory, Long> {

}

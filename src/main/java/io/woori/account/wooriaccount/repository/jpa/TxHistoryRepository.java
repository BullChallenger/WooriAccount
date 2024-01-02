package io.woori.account.wooriaccount.repository.jpa;




import org.springframework.data.repository.Repository;

import io.woori.account.wooriaccount.domain.entity.AbstractTxHistory;
import io.woori.account.wooriaccount.repository.common.CommonTxHistoryRepository;



public interface TxHistoryRepository extends Repository<AbstractTxHistory, Long>, CommonTxHistoryRepository {

}

package io.woori.account.wooriaccount.repository.common;

import io.woori.account.wooriaccount.domain.entity.AbstractTxHistory;
import io.woori.account.wooriaccount.domain.entity.Customer;

public interface CommonTxHistoryRepository {

    AbstractTxHistory findById(Long id);
    void deleteById(Long id);
    void save(AbstractTxHistory txHistory);

}

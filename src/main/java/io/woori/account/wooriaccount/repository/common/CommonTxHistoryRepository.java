package io.woori.account.wooriaccount.repository.common;

import io.woori.account.wooriaccount.domain.entity.AbstractTxHistory;
import io.woori.account.wooriaccount.domain.entity.Customer;

import java.util.Optional;

public interface CommonTxHistoryRepository {

    Optional<AbstractTxHistory> findById(Long id);
    void deleteById(Long id);
    AbstractTxHistory save(AbstractTxHistory txHistory);

}

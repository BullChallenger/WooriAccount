package io.woori.account.wooriaccount.repository.common;

import io.woori.account.wooriaccount.domain.entity.AbstractTxHistory;
import io.woori.account.wooriaccount.domain.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CommonTxHistoryRepository<Tx> {

    Optional<Tx> findById(Long id);
    void deleteById(Long id);
    Tx save(Tx tx);

}

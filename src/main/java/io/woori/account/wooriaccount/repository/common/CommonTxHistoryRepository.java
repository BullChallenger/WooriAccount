package io.woori.account.wooriaccount.repository.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommonTxHistoryRepository<Tx> {

    Optional<Tx> findById(Long id);
    void deleteById(Long id);
    Tx save(Tx tx);

}

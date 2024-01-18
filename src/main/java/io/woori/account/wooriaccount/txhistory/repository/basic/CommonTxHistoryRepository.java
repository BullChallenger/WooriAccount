package io.woori.account.wooriaccount.txhistory.repository.basic;

import java.util.Optional;

public interface CommonTxHistoryRepository<Tx> {

	Optional<Tx> findById(Long id);

	void deleteById(Long id);

	Tx save(Tx tx);

}

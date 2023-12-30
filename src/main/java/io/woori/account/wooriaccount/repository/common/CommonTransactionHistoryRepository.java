package io.woori.account.wooriaccount.repository.common;

import io.woori.account.wooriaccount.domain.entity.TransactionHistory;

public interface CommonTransactionHistoryRepository {

    TransactionHistory findById(Long id);

}

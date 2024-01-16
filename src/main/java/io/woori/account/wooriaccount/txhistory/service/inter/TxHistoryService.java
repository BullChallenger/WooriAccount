package io.woori.account.wooriaccount.txhistory.service.inter;

import io.woori.account.wooriaccount.txhistory.domain.dto.SaveTxRequestDTO;

public interface TxHistoryService<T, ID> {

    T save(SaveTxRequestDTO dto);

    T findTxById(ID txId);

}

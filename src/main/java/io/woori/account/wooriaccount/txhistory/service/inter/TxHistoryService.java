package io.woori.account.wooriaccount.txhistory.service.inter;

import io.woori.account.wooriaccount.txhistory.domain.AbstractTxHistory;
import io.woori.account.wooriaccount.txhistory.domain.dto.SaveTxRequestDTO;

public interface TxHistoryService<T extends AbstractTxHistory, ID> {

    T save(SaveTxRequestDTO dto);

    T findTxById(ID txId);

}

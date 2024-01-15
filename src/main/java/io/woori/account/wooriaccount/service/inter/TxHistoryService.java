package io.woori.account.wooriaccount.service.inter;

import io.woori.account.wooriaccount.domain.entity.AbstractTxHistory;
import io.woori.account.wooriaccount.dto.tx.FindAllTxResponseDTO;
import io.woori.account.wooriaccount.dto.tx.SaveTxRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TxHistoryService<T extends AbstractTxHistory, ID> {

    T save(SaveTxRequestDTO dto);

    T findTxById(ID txId);

}

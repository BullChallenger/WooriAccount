package io.woori.account.wooriaccount.service.inter;

import io.woori.account.wooriaccount.domain.entity.AbstractTxHistory;
import io.woori.account.wooriaccount.dto.tx.SaveTxRequestDTO;
import io.woori.account.wooriaccount.repository.jpa.TxHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface TxHistoryService<T extends AbstractTxHistory, ID> {

    T save(SaveTxRequestDTO dto);

    T findTxById(ID txId);

}

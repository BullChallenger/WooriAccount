package io.woori.account.wooriaccount.service;

import io.woori.account.wooriaccount.domain.entity.AbstractTxHistory;
import io.woori.account.wooriaccount.dto.tx.FindAllTxResponseDTO;
import io.woori.account.wooriaccount.repository.jpa.TxHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TxServiceImpl {

    private final TxHistoryRepository<AbstractTxHistory> txHistoryRepository;

    public Page<FindAllTxResponseDTO> findBySenderIdOrReceiverId(Long accountId, Pageable pageable) {
        return txHistoryRepository.findBySenderAccountIdOrReceiverAccountIdOrderByCreatedTimeDesc(accountId, accountId, pageable).map(FindAllTxResponseDTO::of);
    }

}

package io.woori.account.wooriaccount.service;

import io.woori.account.wooriaccount.domain.entity.DepositTxHistory;
import io.woori.account.wooriaccount.dto.tx.FindAllDepositTxResponseDTO;
import io.woori.account.wooriaccount.dto.tx.SaveTxRequestDTO;
import io.woori.account.wooriaccount.exception.ErrorCode;
import io.woori.account.wooriaccount.exception.TxHistoryException;
import io.woori.account.wooriaccount.repository.jpa.TxHistoryRepository;
import io.woori.account.wooriaccount.repository.querydsl.QueryTransactionHistoryRepositoryImpl;
import io.woori.account.wooriaccount.service.inter.TxHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DepositTxServiceImpl implements TxHistoryService<DepositTxHistory, Long> {

    private final TxHistoryRepository<DepositTxHistory> txHistoryRepository;
    private final QueryTransactionHistoryRepositoryImpl queryTransactionHistoryRepository;

    @Override
    public DepositTxHistory save(SaveTxRequestDTO dto) {
        return DepositTxHistory.of(
                dto.getSender(),
                dto.getReceiver(),
                dto.getAmount(),
                dto.getBalanceAfterTx(),
                dto.getDescription()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public DepositTxHistory findTxById(Long txId) {
        return txHistoryRepository.findById(txId).orElseThrow(
                () -> new TxHistoryException(ErrorCode.NOT_FOUND_TX)
        );
    }

    @Transactional
    public Page<FindAllDepositTxResponseDTO> findTxHistoryAll(Long accountId,
                                                              Long lastTxHistoryId,
                                                              Pageable pageable)
    {
        return queryTransactionHistoryRepository
                .readDepositTxHistoryAll(accountId, lastTxHistoryId, pageable);
    }

}

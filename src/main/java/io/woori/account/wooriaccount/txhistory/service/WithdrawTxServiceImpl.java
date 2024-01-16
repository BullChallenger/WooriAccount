package io.woori.account.wooriaccount.txhistory.service;

import io.woori.account.wooriaccount.txhistory.domain.WithdrawTxHistory;
import io.woori.account.wooriaccount.txhistory.domain.dto.FindAllWithdrawTxResponseDTO;
import io.woori.account.wooriaccount.txhistory.domain.dto.SaveTxRequestDTO;
import io.woori.account.wooriaccount.common.exception.ErrorCode;
import io.woori.account.wooriaccount.common.exception.TxHistoryException;
import io.woori.account.wooriaccount.txhistory.repository.jpa.WithdrawTxHistoryRepository;
import io.woori.account.wooriaccount.txhistory.repository.querydsl.QueryTransactionHistoryRepositoryImpl;
import io.woori.account.wooriaccount.txhistory.service.inter.TxHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WithdrawTxServiceImpl implements TxHistoryService<WithdrawTxHistory, Long> {

    private final WithdrawTxHistoryRepository txHistoryRepository;
    private final QueryTransactionHistoryRepositoryImpl queryTransactionHistoryRepository;

    @Override
    public WithdrawTxHistory save(SaveTxRequestDTO dto) {
        return WithdrawTxHistory.of(
                dto.getSender(),
                dto.getReceiver(),
                dto.getAmount(),
                dto.getBalanceAfterTx(),
                dto.getDescription());
    }

    @Override
    @Transactional(readOnly = true)
    public WithdrawTxHistory findTxById(Long txId) {
        return txHistoryRepository.findById(txId).orElseThrow(
                () -> new TxHistoryException(ErrorCode.NOT_FOUND_TX)
        );
    }

    @Transactional
    public Page<FindAllWithdrawTxResponseDTO> findTxHistoryAll(Long accountId,
                                                               Long lastTxHistoryId,
                                                               Pageable pageable)
    {
        return queryTransactionHistoryRepository
                .readWithdrawTxHistoryAll(accountId, lastTxHistoryId, pageable);
    }

}


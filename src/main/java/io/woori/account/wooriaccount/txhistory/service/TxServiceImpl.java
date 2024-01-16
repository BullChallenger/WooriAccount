package io.woori.account.wooriaccount.txhistory.service;

import io.woori.account.wooriaccount.txhistory.domain.dto.FindAllTxResponseDTO;
import io.woori.account.wooriaccount.txhistory.repository.querydsl.QueryTransactionHistoryRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TxServiceImpl {

    private final QueryTransactionHistoryRepositoryImpl txHistoryRepository;

    public Page<FindAllTxResponseDTO> findBySenderIdOrReceiverId(Long accountId, Pageable pageable) {
        List<FindAllTxResponseDTO> allMyTxHistory = new ArrayList<>();
        List<FindAllTxResponseDTO> byDepositTx = txHistoryRepository.readDepositTxHistoryAllToList(accountId)
                .stream().map(FindAllTxResponseDTO::of).toList();
        List<FindAllTxResponseDTO> byWithdrawTx = txHistoryRepository.readWithdrawTxHistoryAllToList(accountId)
                .stream().map(FindAllTxResponseDTO::of).toList();

        allMyTxHistory.addAll(byDepositTx);
        allMyTxHistory.addAll(byWithdrawTx);
        Collections.sort(allMyTxHistory);

        return new PageImpl<>(allMyTxHistory, pageable, allMyTxHistory.size());
    }

}

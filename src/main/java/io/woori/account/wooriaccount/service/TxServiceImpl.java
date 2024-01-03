package io.woori.account.wooriaccount.service;

import io.woori.account.wooriaccount.domain.entity.AbstractTxHistory;
import io.woori.account.wooriaccount.domain.entity.DepositTxHistory;
import io.woori.account.wooriaccount.domain.entity.WithdrawTxHistory;
import io.woori.account.wooriaccount.dto.tx.FindAllTxResponseDTO;
import io.woori.account.wooriaccount.repository.jpa.DepositTxHistoryRepository;
import io.woori.account.wooriaccount.repository.jpa.TxHistoryRepository;
import io.woori.account.wooriaccount.repository.jpa.WithdrawTxHistoryRepository;
import io.woori.account.wooriaccount.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TxServiceImpl {

    private final DepositTxHistoryRepository depositTxHistoryRepository;
    private final WithdrawTxHistoryRepository withdrawTxHistoryRepository;

    public Page<FindAllTxResponseDTO> findBySenderIdOrReceiverId(Long accountId, Pageable pageable) {
        List<FindAllTxResponseDTO> allMyTxHistory = new ArrayList<>();
        List<FindAllTxResponseDTO> bySenderAccountId = depositTxHistoryRepository.findByReceiverAccountId(accountId)
                .stream().map(FindAllTxResponseDTO::of).toList();
        List<FindAllTxResponseDTO> byReceiverAccountId = withdrawTxHistoryRepository.findBySenderAccountId(accountId)
                .stream().map(FindAllTxResponseDTO::of).toList();

        allMyTxHistory.addAll(bySenderAccountId);
        allMyTxHistory.addAll(byReceiverAccountId);
        Collections.sort(allMyTxHistory);

        return new PageImpl<>(allMyTxHistory, pageable, allMyTxHistory.size());
    }

}

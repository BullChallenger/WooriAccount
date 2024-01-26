package io.woori.account.wooriaccount.txhistory.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.woori.account.wooriaccount.txhistory.domain.dto.FindTxResponseDTO;
import io.woori.account.wooriaccount.txhistory.repository.querydsl.QueryTransactionHistoryRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TxServiceImpl {

	private final QueryTransactionHistoryRepositoryImpl txHistoryRepository;

	@Transactional
	public Page<FindTxResponseDTO> findBySenderIdOrReceiverId(Long accountId, Pageable pageable) {
		List<FindTxResponseDTO> allMyTxHistory = new ArrayList<>();

		allMyTxHistory.addAll(txHistoryRepository.readDepositTxHistoryAllToList(accountId));
		allMyTxHistory.addAll(txHistoryRepository.readWithdrawTxHistoryAllToList(accountId));
		Collections.sort(allMyTxHistory);

		return new PageImpl<>(allMyTxHistory, pageable, allMyTxHistory.size());
	}

}

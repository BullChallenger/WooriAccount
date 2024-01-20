package io.woori.account.wooriaccount.txhistory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.woori.account.wooriaccount.common.exception.ErrorCode;
import io.woori.account.wooriaccount.common.exception.TxHistoryException;
import io.woori.account.wooriaccount.txhistory.domain.DepositTxHistory;
import io.woori.account.wooriaccount.txhistory.domain.dto.FindTxResponseDTO;
import io.woori.account.wooriaccount.txhistory.domain.dto.SaveTxRequestDTO;
import io.woori.account.wooriaccount.txhistory.repository.jpa.DepositTxHistoryRepository;
import io.woori.account.wooriaccount.txhistory.repository.querydsl.QueryTransactionHistoryRepositoryImpl;
import io.woori.account.wooriaccount.txhistory.service.inter.TxHistoryService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DepositTxServiceImpl implements TxHistoryService<DepositTxHistory, Long> {

	private final DepositTxHistoryRepository txHistoryRepository;
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
	public Page<FindTxResponseDTO> findTxHistoryAll(
		Long accountId,
		Long lastTxHistoryId,
		Pageable pageable
	) {
		return queryTransactionHistoryRepository
			.readDepositTxHistoryAll(accountId, lastTxHistoryId, pageable);
	}

}

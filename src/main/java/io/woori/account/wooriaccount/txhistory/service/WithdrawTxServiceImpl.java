package io.woori.account.wooriaccount.txhistory.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.woori.account.wooriaccount.account.domain.entity.Account;
import io.woori.account.wooriaccount.account.repository.jpa.AccountRepository;
import io.woori.account.wooriaccount.common.exception.CustomException;
import io.woori.account.wooriaccount.common.exception.ErrorCode;
import io.woori.account.wooriaccount.common.exception.TxHistoryException;
import io.woori.account.wooriaccount.txhistory.domain.WithdrawTxHistory;
import io.woori.account.wooriaccount.txhistory.domain.dto.FindAllWithdrawTxResponseDTO;
import io.woori.account.wooriaccount.txhistory.domain.dto.SaveTxRequestDTO;
import io.woori.account.wooriaccount.txhistory.repository.jpa.WithdrawTxHistoryRepository;
import io.woori.account.wooriaccount.txhistory.repository.querydsl.QueryTransactionHistoryRepositoryImpl;
import io.woori.account.wooriaccount.txhistory.service.inter.TxHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WithdrawTxServiceImpl implements TxHistoryService<WithdrawTxHistory, Long> {

	private final AccountRepository accountRepository;
	private final WithdrawTxHistoryRepository txHistoryRepository;
	private final QueryTransactionHistoryRepositoryImpl queryTransactionHistoryRepository;

	@Override
	public WithdrawTxHistory save(SaveTxRequestDTO dto) {
		return WithdrawTxHistory.of(
			dto.getSender(),
			dto.getReceiver(),
			dto.getAmount(),
			dto.getBalanceAfterTx(),
			dto.getDescription()
		);
	}

	@Override
	@Transactional(readOnly = true)
	public WithdrawTxHistory findTxById(Long txId) {
		return txHistoryRepository.findById(txId).orElseThrow(
			() -> new TxHistoryException(ErrorCode.NOT_FOUND_TX)
		);
	}

	@Transactional
	public Page<FindAllWithdrawTxResponseDTO> findTxHistoryAll(
		Long accountId,
		Long lastTxHistoryId,
		Pageable pageable
	) {
		return queryTransactionHistoryRepository
			.readWithdrawTxHistoryAll(accountId, lastTxHistoryId, pageable);
	}

	@Transactional
	public WithdrawTxHistory withdraw(String accountNumber, String amount) {
		BigDecimal withdrawAmount = BigDecimal.valueOf(Long.parseLong(amount));

		final Account account = accountRepository.findByAccountNumber(accountNumber)
			.orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

		if (account.getAccountBalance().compareTo(withdrawAmount) < 0) {
			throw new CustomException(ErrorCode.INSUFFICIENT_FUNDS);
		}

		account.setAccountBalance(account.getAccountBalance().subtract(withdrawAmount));

		WithdrawTxHistory withdrawTxHistory = WithdrawTxHistory.of(
			account,
			account,
			withdrawAmount,
			account.getAccountBalance(),
			"Withdrawal"
		);

		withdrawTxHistory = txHistoryRepository.save(withdrawTxHistory);

		notifyTx(account, withdrawAmount);

		return withdrawTxHistory;
	}

	private void notifyTx(Account account, BigDecimal amount) {
		List<Long> accountIds = new ArrayList<>();
		accountIds.add(account.getAccountId());
		//? 모르겠음
	}
}


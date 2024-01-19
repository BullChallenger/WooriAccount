package io.woori.account.wooriaccount.txhistory.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.woori.account.wooriaccount.txhistory.domain.WithdrawTxHistory;
import io.woori.account.wooriaccount.txhistory.domain.dto.FindAllDepositTxResponseDTO;
import io.woori.account.wooriaccount.txhistory.domain.dto.FindAllTxResponseDTO;
import io.woori.account.wooriaccount.txhistory.domain.dto.FindAllWithdrawTxResponseDTO;
import io.woori.account.wooriaccount.txhistory.service.DepositTxServiceImpl;
import io.woori.account.wooriaccount.txhistory.service.TxServiceImpl;
import io.woori.account.wooriaccount.txhistory.service.WithdrawTxServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/tx")
public class TxController {

	private final DepositTxServiceImpl depositTxService;
	private final WithdrawTxServiceImpl withdrawTxService;
	private final TxServiceImpl txService;

	@GetMapping(value = "/deposit/{accountId}/{lastTxHistoryId}")
	public ResponseEntity<Page<FindAllDepositTxResponseDTO>> readDepositTxHistory(
		@PathVariable(value = "accountId") Long accountId,
		@PathVariable(value = "lastTxHistoryId") Long lastTxHistoryId,
		Pageable pageable
	) {
		return ResponseEntity.ok(depositTxService.findTxHistoryAll(accountId, lastTxHistoryId, pageable));
	}

	@GetMapping(value = "/withdraw/{accountId}/{lastTxHistoryId}")
	public ResponseEntity<Page<FindAllWithdrawTxResponseDTO>> readWithdrawTxHistory(
		@PathVariable(value = "accountId") Long accountId,
		@PathVariable(value = "lastTxHistoryId") Long lastTxHistoryId,
		Pageable pageable
	) {
		return ResponseEntity.ok(withdrawTxService.findTxHistoryAll(accountId, lastTxHistoryId, pageable));
	}

	@GetMapping(value = "/all/{accountId}")
	public ResponseEntity<Page<FindAllTxResponseDTO>> readAllTxHistory(
		@PathVariable Long accountId,
		Pageable pageable
	) {
		return ResponseEntity.ok(txService.findBySenderIdOrReceiverId(accountId, pageable));
	}

	@PostMapping(value = "/withdraw")
	public ResponseEntity<WithdrawTxHistory> withdraw(
		@RequestParam("accountNumber") String accountNumber,
		@RequestParam("amount") String amount
	) {
		WithdrawTxHistory withdrawTxHistory = withdrawTxService.withdraw(accountNumber, amount);
		return ResponseEntity.ok(withdrawTxHistory);
	}

}

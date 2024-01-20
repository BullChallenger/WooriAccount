package io.woori.account.wooriaccount.account.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.woori.account.wooriaccount.account.domain.dto.AccountAllDTO;
import io.woori.account.wooriaccount.account.domain.dto.AccountDTO;
import io.woori.account.wooriaccount.account.domain.dto.AccountRemittanceDTO;
import io.woori.account.wooriaccount.account.service.AccountServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

	private final AccountServiceImpl accountService;

	@PostMapping("/create/{customerId}")
	public ResponseEntity<AccountDTO> createAccount(@PathVariable("customerId") Long customerId) {

		return ResponseEntity.ok(accountService.accountCreate(customerId));
	}

	@PostMapping("/delete/{accountNumber}")
	public ResponseEntity<AccountDTO> deletedAccount(@PathVariable("accountNumber") String accountNumber) {

		return ResponseEntity.ok(accountService.accountDelete(accountNumber));

	}

	@GetMapping("/{accountNumber}")
	public ResponseEntity<AccountDTO> getAccountByAccountNumber(@PathVariable String accountNumber) {

		return ResponseEntity.ok(accountService.accountInquiry(accountNumber));

	}

	@PostMapping("/remittance")
	public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountRemittanceDTO dto) {

		return ResponseEntity.ok(accountService.accountRemittance(dto));

	}

	@GetMapping("/find")
	public ResponseEntity<List<AccountAllDTO>> findAllAccount(Long id) {
		return ResponseEntity.ok(accountService.findAllByCustomerId(id));
	}

}

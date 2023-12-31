package io.woori.account.wooriaccount.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import io.woori.account.wooriaccount.domain.entity.*;
import io.woori.account.wooriaccount.dto.account.AccountAllDTO;
import io.woori.account.wooriaccount.dto.account.AccountRemittanceDTO;
import io.woori.account.wooriaccount.exception.CustomException;
import io.woori.account.wooriaccount.exception.ErrorCode;

import io.woori.account.wooriaccount.repository.jpa.TxHistoryRepository;
import org.springframework.stereotype.Service;

import io.woori.account.wooriaccount.dto.account.AccountDTO;
import io.woori.account.wooriaccount.repository.jpa.AccountRepository;
import io.woori.account.wooriaccount.repository.jpa.CustomerRepository;
import io.woori.account.wooriaccount.repository.querydsl.inter.QueryAccountRepository;
import io.woori.account.wooriaccount.service.inter.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService{
	
	
	private final AccountRepository accountRepository;
	private final QueryAccountRepository queryAccountRepository;
	private final CustomerRepository customerRepository;
	private final TxHistoryRepository<DepositTxHistory> depositTxHistoryRepository;
	private final TxHistoryRepository<WithdrawTxHistory> withdrawTxHistoryRepository;

	@Override
	public AccountDTO accountInquiry(String accountNumber) {
		Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
		Account account = optionalAccount.orElseThrow(() ->
				new CustomException(ErrorCode.ACCOUNT_NOT_FOUND)
		);


		return AccountDTO.fromEntity(account);
	}

	@Override
	@Transactional
	public AccountDTO accountRemittance(AccountRemittanceDTO dto) {

		//출금 계좌 조회
		Account sourceAccount = accountRepository.findByAccountNumber(dto.getAccountNumber())
				.orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

		// 입금 계좌 조회
		Account targetAccount = accountRepository.findByAccountNumber(dto.getTargetAccountNumber())
				.orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));


		// 잔액 확인
		if (sourceAccount.getAccountBalance().compareTo(BigDecimal.valueOf(Long.parseLong(dto.getAmount()))) < 0) {
			throw new CustomException(ErrorCode.INSUFFICIENT_FUNDS);
		}

		// 출금 계좌에서 금액 차감
		sourceAccount.setAccountBalance(sourceAccount.getAccountBalance().subtract(BigDecimal.valueOf(Long.parseLong(dto.getAmount()))));

		// 입금 계좌에 금액 추가
		targetAccount.setAccountBalance(targetAccount.getAccountBalance().add(BigDecimal.valueOf(Long.parseLong(dto.getAmount()))));

		// 거래 내역 기록
		WithdrawTxHistory withedDrawCreateTransactionHistory = withDrawCreateTransactionHistory(sourceAccount, targetAccount, BigDecimal.valueOf(Long.parseLong(dto.getAmount())),dto.getDescription() );
		DepositTxHistory depositCreateTransactionHistory = depositCreateTransactionHistory(targetAccount, sourceAccount, BigDecimal.valueOf(Long.parseLong(dto.getAmount())), dto.getDescription());



		withdrawTxHistoryRepository.save(withedDrawCreateTransactionHistory);
		depositTxHistoryRepository.save(depositCreateTransactionHistory);


		return AccountDTO.fromEntity(sourceAccount);

	}


	@Transactional
	@Override
	public AccountDTO accountCreate(Long customerId) {
		customerRepository.findById(customerId).orElseThrow();

		
		Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));
		
		//8자리 난수 생성
		int randomNumber = (int)(Math.random() * 89999999) + 10000000;


		//생성한 난수 번호가 이미 존재한다면 사용하지 못하게 한다.
		Optional<Account> op = accountRepository.findByAccountNumber(String.valueOf(randomNumber));

		if (op.isPresent()){
			throw new CustomException(ErrorCode.NOT_FOUND_ACCOUNT);
		}

		// 기본값으로 fix
		Account account = Account.createAccount(String.valueOf(randomNumber), BigDecimal.valueOf(1000), BigDecimal.valueOf(1000000), customer);
		accountRepository.save(account);

		return AccountDTO.builder()
				.accountId(account.getAccountId())
				.accountNumber(account.getAccountNumber())
				.accountBalance(account.getAccountLimit())
				.customerName(customer.getCustomerName())
				.build();

	}

	@Override
	public AccountDTO accountDelete(String accountNumber) {


		Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ACCOUNT));

		Account deletedAccount = account.deletedAccount(account.getAccountNumber());

		return AccountDTO.fromEntity(deletedAccount);
	}



	@Override
	public List<AccountAllDTO> findAllByCustomerId(Long id) {

		List<Account> accounts = queryAccountRepository.queryFindAllByCustomerId(id);
		if (Objects.isNull(accounts)){
			throw new CustomException(ErrorCode.NOT_FOUNT_ACCOUNT_LIST);

		}

		List<AccountAllDTO> list = extractedDTO(accounts);

		return list;
	}

	private static List<AccountAllDTO> extractedDTO(List<Account> accounts) {
		List<AccountAllDTO> list = new ArrayList<>();
		for (Account account : accounts) {

			list.add(AccountAllDTO.fromEntity(account));
		}

		return list;
	}


	private WithdrawTxHistory withDrawCreateTransactionHistory(Account sourceAccount,
															   Account targetAccount,
															   BigDecimal amount, String description) {

		return WithdrawTxHistory.of(sourceAccount, targetAccount, amount, sourceAccount.getAccountBalance(), description);

	}

	private DepositTxHistory depositCreateTransactionHistory(Account sourceAccount, Account targetAccount, BigDecimal amount,
															  String description) {

		return DepositTxHistory.of(sourceAccount, targetAccount, amount, sourceAccount.getAccountBalance(), description);

	}

	

}

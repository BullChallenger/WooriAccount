package io.woori.account.wooriaccount.service;

import java.math.BigDecimal;
import java.util.Optional;

import io.woori.account.wooriaccount.domain.entity.Customer;
import io.woori.account.wooriaccount.exception.CustomException;
import io.woori.account.wooriaccount.exception.ErrorCode;

import org.springframework.stereotype.Service;

import io.woori.account.wooriaccount.domain.entity.Account;
import io.woori.account.wooriaccount.dto.account.AccountDTO;
import io.woori.account.wooriaccount.repository.jpa.AccountRepository;
import io.woori.account.wooriaccount.repository.jpa.CustomerRepository;
import io.woori.account.wooriaccount.repository.querydsl.inter.QueryAccountRepository;
import io.woori.account.wooriaccount.service.inter.AccountService;
import lombok.RequiredArgsConstructor;




@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService{
	
	
	private final AccountRepository accountRepository;
	private final QueryAccountRepository queryAccountRepository;
	private final CustomerRepository customerRepository;
	
	@Override
	public AccountDTO AccountInquiry(String accountNumber) {
		Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
		Account account = optionalAccount.orElseThrow(() ->
				new CustomException(ErrorCode.ACCOUNT_NOT_FOUND)
		);
		return AccountDTO.fromEntity(account);
	}

	@Override
	public AccountDTO accountRemittance(String accountNumber, String targetAccountNumber) {
		// TODO Auto-generated method stub
		return null;
	}


	
	@Override
	public AccountDTO accountCreate(Long customerId, BigDecimal accountBalance, BigDecimal accountLimit) {
		customerRepository.findById(customerId).orElseThrow();

		
		Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));
		
		//8자리 난수 생성
		int randomNumber = (int)(Math.random() * 89999999) + 10000000;

		Optional<Account> op = accountRepository.findByAccountNumber(String.valueOf(randomNumber));

		if (op.isPresent()){
			throw new CustomException(ErrorCode.NOT_FOUND_ACCOUNT);

		}
		Account account = Account.createAccount(String.valueOf(randomNumber), accountBalance, accountLimit, customer);

		return AccountDTO.builder()
				.accountId(account.getAccountId())
				.accountNumber(account.getAccountNumber())
				.accountBalance(account.getAccountLimit())
				.customer(customer)
				.build();

	}

	@Override
	public AccountDTO accountDelete(String accountNumber) {


		Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ACCOUNT));

		Account deletedAccount = account.deletedAccount(account.getAccountNumber());


		return AccountDTO.fromEntity(deletedAccount);
	}



	

}

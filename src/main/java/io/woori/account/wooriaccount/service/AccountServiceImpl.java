package io.woori.account.wooriaccount.service;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.woori.account.wooriaccount.domain.entity.Account;
import io.woori.account.wooriaccount.domain.entity.Customer;
import io.woori.account.wooriaccount.dto.account.AccountDTO;
import io.woori.account.wooriaccount.exception.CustomException;
import io.woori.account.wooriaccount.exception.ErrorCode;
import io.woori.account.wooriaccount.repository.jpa.AccountRepository;
import io.woori.account.wooriaccount.repository.jpa.CustomerRepository;
import io.woori.account.wooriaccount.repository.querydsl.inter.QueryAccountRepository;
import io.woori.account.wooriaccount.service.inter.AccountService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService{
	
	
	private final AccountRepository accountRepository;
	private final QueryAccountRepository queryAccountRepository;
	private final CustomerRepository customerRepository;
	
	@Override
	public AccountDTO AccountInquiry(String accountNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountDTO accountRemittance(String accountNumber, String targetAccountNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	@Override
	public AccountDTO accountCreate(Long customerId, BigDecimal accountBalance, BigDecimal accountLimit) {
		
		
		Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));
		
		//8자리 난수 생성
		int randomNumber = (int)(Math.random() * 89999999) + 10000000;

		Optional<Account> op = accountRepository.findByAccountNumber(String.valueOf(randomNumber));

		if (op.isPresent()){
			throw new CustomException(ErrorCode.NOT_FOUND_ACCOUNT);

		}
		Account.createAccount(String.valueOf(randomNumber), accountBalance, accountLimit, customer);






		return null;
	}

	@Override
	public AccountDTO accountDelete(String accountNumber) {
		// TODO Auto-generated method stub
		return null;
	}



	

}

package io.woori.account.wooriaccount.service;
import io.woori.account.wooriaccount.exception.CustomException;
import io.woori.account.wooriaccount.exception.ErrorCode;
import org.springframework.stereotype.Service;

import io.woori.account.wooriaccount.domain.entity.Account;
import io.woori.account.wooriaccount.dto.account.AccountDTO;
import io.woori.account.wooriaccount.repository.jpa.AccountRepository;
import io.woori.account.wooriaccount.repository.jpa.CustomerRepository;
import io.woori.account.wooriaccount.repository.querydsl.inter.QueryAccountRepository;
import io.woori.account.wooriaccount.service.inter.AccountService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static io.woori.account.wooriaccount.dto.account.AccountDTO.fromEntity;


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
	public AccountDTO accontCreate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountDTO accountDelete(String accountNumber) {
		// TODO Auto-generated method stub
		return null;
	}



	

}

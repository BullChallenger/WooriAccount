package io.woori.account.wooriaccount.repository.querydsl.inter;

import io.woori.account.wooriaccount.domain.entity.Account;

public interface QueryAccountRepository {

//    Account queryFindById(Long id);
	
	Account queryFindByAccountNumber(String accountNumber);
	

}

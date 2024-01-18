package io.woori.account.wooriaccount.account.repository.querydsl;

import java.util.List;
import java.util.Optional;

import io.woori.account.wooriaccount.account.domain.entity.Account;

public interface QueryAccountRepository {

	//    Account queryFindById(Long id);

	/*
	 * query dsl을 이용해서 계좌번호를 이용해 해당 계좌를 optional 객체로 받아옵니다.
	 *
	 * @param accountNumber 조회하려는 계좌번호
	 * @return 조회된 Optional 계좌 객체
	 * */
	Optional<Account> queryFindByAccountNumber(String accountNumber);

	/*
	 * query dsl을 이용해서 계좌 정보를 모두 돌려줍니다.
	 *
	 * @param id  회원 pk
	 * @return 모든 계좌 정보
	 * */
	List<Account> queryFindAllByCustomerId(Long id);

}

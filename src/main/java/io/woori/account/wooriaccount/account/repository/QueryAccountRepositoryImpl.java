package io.woori.account.wooriaccount.account.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.woori.account.wooriaccount.account.domain.entity.Account;
import io.woori.account.wooriaccount.account.domain.entity.QAccount;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

/*
* queryDsl을 사용한 레포지토리 입니다.
* @author yeom hwiju
* */
@Repository
@RequiredArgsConstructor
public class QueryAccountRepositoryImpl implements QueryAccountRepository {

    private final JPAQueryFactory jpaQueryFactory;


	@Override
	public Optional<Account> queryFindByAccountNumber(String accountNumber) {

		QAccount account = QAccount.account;

		return Optional.ofNullable(jpaQueryFactory
				.selectFrom(account)
				.where(account.accountNumber.eq(accountNumber)).fetchOne());
		
	}

	@Override
	public List<Account> queryFindAllByCustomerId(Long id) {
		QAccount account = QAccount.account;
		return jpaQueryFactory.selectFrom(account)
				.where(account.customer.customerId.eq(id))
				.fetch();
	}


}

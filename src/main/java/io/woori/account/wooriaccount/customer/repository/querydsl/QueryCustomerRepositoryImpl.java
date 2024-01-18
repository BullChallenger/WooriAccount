package io.woori.account.wooriaccount.customer.repository.querydsl;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

/*
 * queryDsl을 사용한 레포지토리 입니다.
 * @author yeom hwiju
 * */
@Repository
@RequiredArgsConstructor
public class QueryCustomerRepositoryImpl implements QueryCustomerRepository {

	private final JPAQueryFactory jpaQueryFactory;
}

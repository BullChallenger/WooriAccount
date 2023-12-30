package io.woori.account.wooriaccount.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.woori.account.wooriaccount.repository.querydsl.inter.QueryCustomerRepository;
import io.woori.account.wooriaccount.repository.querydsl.inter.QueryTransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/*
* queryDsl을 사용한 레포지토리 입니다.
* @author yeom hwiju
* */
@Repository
@RequiredArgsConstructor
public class QueryTransactionHistoryRepositoryImpl implements QueryTransactionHistoryRepository {

    private final JPAQueryFactory jpaQueryFactory;

}

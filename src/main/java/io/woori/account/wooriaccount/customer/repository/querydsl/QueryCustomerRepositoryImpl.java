package io.woori.account.wooriaccount.customer.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
//import io.woori.account.wooriaccount.domain.entity.QAccount;
import io.woori.account.wooriaccount.customer.repository.querydsl.QueryCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/*
* queryDsl을 사용한 레포지토리 입니다.
* @author yeom hwiju
* */
@Repository
@RequiredArgsConstructor
public class QueryCustomerRepositoryImpl implements QueryCustomerRepository {

    private final JPAQueryFactory jpaQueryFactory;

}

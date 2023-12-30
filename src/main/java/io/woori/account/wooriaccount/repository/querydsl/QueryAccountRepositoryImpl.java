package io.woori.account.wooriaccount.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.woori.account.wooriaccount.domain.entity.Account;
import io.woori.account.wooriaccount.domain.entity.QAccount;
import io.woori.account.wooriaccount.repository.querydsl.inter.QueryAccountRepository;
import lombok.RequiredArgsConstructor;
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
    public Account queryFindById(Long id) {
        QAccount account = QAccount.account;

        return jpaQueryFactory
                .selectFrom(account)
                .where(account.id.eq(id))
                .fetchOne();
    }
}

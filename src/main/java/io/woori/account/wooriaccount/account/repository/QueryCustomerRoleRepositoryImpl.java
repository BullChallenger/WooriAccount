package io.woori.account.wooriaccount.repository.querydsl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import io.woori.account.wooriaccount.domain.entity.CustomerRole;
import io.woori.account.wooriaccount.domain.entity.QCustomerRole;
import io.woori.account.wooriaccount.repository.querydsl.inter.QueryCustomerRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueryCustomerRoleRepositoryImpl implements QueryCustomerRoleRepository {

    private final JPAQueryFactory factory;


    @Override
    public List<CustomerRole> findByCustomerEmail(String email) {
        QCustomerRole qCustomerRole = QCustomerRole.customerRole;
        return factory.selectFrom(qCustomerRole)
                .where(qCustomerRole.customer.customerEmail.eq(email))
                .fetch();
    }
}

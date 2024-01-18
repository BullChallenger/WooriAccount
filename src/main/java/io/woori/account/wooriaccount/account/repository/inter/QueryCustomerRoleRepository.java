package io.woori.account.wooriaccount.repository.querydsl.inter;

import io.woori.account.wooriaccount.domain.entity.CustomerRole;

import java.util.List;

public interface QueryCustomerRoleRepository {

    /*
    * 해당 email 값으로 모든 customer role 테이블에 저장된 값을 가져옵니다.
    * */
    List<CustomerRole> findByCustomerEmail(String email);

}

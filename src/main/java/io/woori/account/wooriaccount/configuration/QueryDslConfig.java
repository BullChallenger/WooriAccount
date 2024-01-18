package io.woori.account.wooriaccount.configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

/*
 * query dsl 관련 설정을 진행하는 클래스입니다.
 *
 * @author yeom hwiju
 * */
@Configuration
public class QueryDslConfig {
	@PersistenceContext
	private EntityManager entityManager;

	public QueryDslConfig() {

	}

	@Bean
	public JPAQueryFactory jpaQueryFactory() {

		return new JPAQueryFactory(this.entityManager);
	}
}

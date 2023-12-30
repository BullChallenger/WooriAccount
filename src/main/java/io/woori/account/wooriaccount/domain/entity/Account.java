package io.woori.account.wooriaccount.domain.entity;


import javax.persistence.*;


/*
* 계좌와 관련된 엔티티 클래스 입니다.
* @author : yeom hwiju
* */
@Table(name = "accounts")
@Entity(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




}

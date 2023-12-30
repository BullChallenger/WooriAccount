package io.woori.account.wooriaccount.domain.entity;


import javax.persistence.*;

@Table(name = "accounts")
@Entity(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




}

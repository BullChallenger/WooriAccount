package io.woori.account.wooriaccount.domain.entity;


import javax.persistence.*;

@Table(name = "customers")
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



}

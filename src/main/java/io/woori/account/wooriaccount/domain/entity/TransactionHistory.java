package io.woori.account.wooriaccount.domain.entity;


import javax.persistence.*;

@Entity
@Table(name = "transaction_history")
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}

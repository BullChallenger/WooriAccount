package io.woori.account.wooriaccount.domain.entity;


import javax.persistence.*;
import java.util.List;


/*
* 계좌와 관련된 엔티티 클래스 입니다.
* @author : yeom hwiju
* */
@Table(name = "accounts")
@Entity(name = "accounts")
public class Account extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId; // 계좌 id
    
    private String accountNumber; // 계좌 번호
    
    private int accountBalance; // 계좌 잔고
    
    private int accountLimit; // 계좌 한도
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer; // 계좌 주인
    
    @OneToMany(mappedBy = "account")
    private List<TransactionHistory> transactionHistories; // 거래내역들
}

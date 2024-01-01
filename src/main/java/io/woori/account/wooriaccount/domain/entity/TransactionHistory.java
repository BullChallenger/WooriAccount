package io.woori.account.wooriaccount.domain.entity;


import javax.persistence.*;

@Entity
@Table(name = "transactions")
public class TransactionHistory extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long txId; // 거래내역 id

    @OneToOne
    @JoinColumn(name = "target_id")
    private Account target; // 대상 , 단방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account; // 어느 계좌 거래내역인지, 양방향

    @Enumerated(EnumType.STRING)
    private TxType type; // 타입 (출금 , 입금)

}

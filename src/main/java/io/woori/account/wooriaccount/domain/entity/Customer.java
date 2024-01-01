package io.woori.account.wooriaccount.domain.entity;


import javax.persistence.*;

@Table(name = "customers")
@Entity
public class Customer extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;  //고객 ID
    private String customerName;  // 고객 이름
    private String customerPhone;  // 고객 전화번호
    private String customerEmail;  // 고객 이메일
    private String customerPwd;  //고객 비밀번호

}

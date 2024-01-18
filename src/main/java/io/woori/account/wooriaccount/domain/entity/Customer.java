package io.woori.account.wooriaccount.domain.entity;


import io.woori.account.wooriaccount.dto.customer.SignUpRequestDTO;
import lombok.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@DynamicInsert
@DynamicUpdate
@Table(name = "customers")
@Where(clause = "IS_DELETED = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;  //고객 ID

    @Column(nullable = false)
    private String customerName;  // 고객 이름

    @Column(nullable = false)
    private String customerPhone;  // 고객 전화번호

    @Column(nullable = false, name = "customer_email")
    private String customerEmail;  // 고객 이메일

    @Column(nullable = false)
    private String customerPwd;  //고객 비밀번호


    public Customer(String customerName, String customerPhone, String customerEmail, String customerPwd) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.customerPwd = customerPwd;
    }

    public static Customer of(SignUpRequestDTO dto, String encodePwd) {
        return Customer.builder()
                .customerName(dto.getCustomerName())
                .customerPhone(dto.getCustomerPhone())
                .customerEmail(dto.getCustomerEmail())
                .customerPwd(encodePwd)
                .build();
    }


}

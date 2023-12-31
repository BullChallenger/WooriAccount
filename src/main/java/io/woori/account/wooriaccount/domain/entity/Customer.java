package io.woori.account.wooriaccount.domain.entity;


import io.woori.account.wooriaccount.dto.user.SignUpRequestDTO;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Getter @Setter
@DynamicInsert
@DynamicUpdate
@Table(name = "customers")
@Where(clause = "IS_DELETED = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;  //고객 ID

    @Column(nullable = false)
    private String customerName;  // 고객 이름
    @Column(nullable = false)
    private String customerPhone;  // 고객 전화번호
    @Column(nullable = false)
    private String customerEmail;  // 고객 이메일
    @Column(nullable = false)
    private String customerPwd;  //고객 비밀번호

    @Builder
    public Customer(String customerName, String customerPhone, String customerEmail, String customerPwd) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.customerPwd = customerPwd;
    }

    @Builder
    public static Customer of(SignUpRequestDTO dto) {
        return Customer.builder()
                .customerName(dto.getCustomerName())
                .customerPhone(dto.getCustomerPhone())
                .customerEmail(dto.getCustomerEmail())
                .customerPwd(dto.getCustomerPwd())
                .build();
    }

}

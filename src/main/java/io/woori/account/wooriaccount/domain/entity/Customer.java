package io.woori.account.wooriaccount.domain.entity;


import io.woori.account.wooriaccount.dto.user.SignUpRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Table(name = "customers")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Where(clause = "IS_DELETED = false")
public class Customer extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;  //고객 ID
    private String customerName;  // 고객 이름
    private String customerPhone;  // 고객 전화번호
    private String customerEmail;  // 고객 이메일
    private String customerPwd;  //고객 비밀번호

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

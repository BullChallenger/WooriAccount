package io.woori.account.wooriaccount.domain.entity;


import io.woori.account.wooriaccount.dto.user.SignUpRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "customers")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;  //고객 ID
    private String customerName;  // 고객 이름
    private String customerPhone;  // 고객 전화번호
    private String customerEmail;  // 고객 이메일
    private String customerPwd;  //고객 비밀번호

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

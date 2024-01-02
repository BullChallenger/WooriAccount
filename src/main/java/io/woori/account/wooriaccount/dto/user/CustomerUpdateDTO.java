package io.woori.account.wooriaccount.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerUpdateDTO {
    private Long customerId;
    private String customerName;  // 고객 이름
    private String customerPhone;  // 고객 전화번호
    private String customerEmail;  // 고객 이메일
    private String customerPwd;  //고객 비밀번호
}

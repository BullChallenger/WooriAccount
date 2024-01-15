package io.woori.account.wooriaccount.dto.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO {
    private String customerName;  // 고객 이름

    private String customerPhone;  // 고객 전화번호


    private String customerEmail;  // 고객 이메일

    private String customerPwd;  //고객 비밀번호
}

package io.woori.account.wooriaccount.dto.customer;

import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO implements Serializable {

    private String email; // 이메일 (로그인 id)
    private String pwd; // 로그인 비번

}

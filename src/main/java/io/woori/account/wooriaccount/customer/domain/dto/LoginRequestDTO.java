package io.woori.account.wooriaccount.customer.domain.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO implements Serializable {
    
	private String email; // 이메일 (로그인 id)
	private String pwd; // 로그인 비번

}

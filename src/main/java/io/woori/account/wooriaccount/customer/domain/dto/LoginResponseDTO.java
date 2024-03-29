package io.woori.account.wooriaccount.customer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {
	private Long customerId;  //고객 ID
	private String customerName;  // 고객 이름
	private String customerPhone;  // 고객 전화번호
	private String customerEmail; // 이메일 (로그인 id)

}

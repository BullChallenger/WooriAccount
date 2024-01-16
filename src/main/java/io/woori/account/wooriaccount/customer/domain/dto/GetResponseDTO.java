package io.woori.account.wooriaccount.customer.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 알람 응답값을 위한 DTO
public class GetResponseDTO {
    private String customerName;  // 고객 이름
    private String customerEmail;  // 고객 이메일
}

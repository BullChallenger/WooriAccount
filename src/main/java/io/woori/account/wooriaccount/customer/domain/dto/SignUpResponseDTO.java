package io.woori.account.wooriaccount.customer.domain.dto;

import io.woori.account.wooriaccount.customer.domain.entity.Customer;
import io.woori.account.wooriaccount.role.domain.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SignUpResponseDTO {

	private Long customerId;
	private String customerName;
	private String customerEmail;
	private String roleName;

	public static SignUpResponseDTO fromEntity(Customer customer, Role role) {

		return SignUpResponseDTO.builder()
			.customerId(customer.getCustomerId())
			.customerName(customer.getCustomerName())
			.customerEmail(customer.getCustomerEmail())
			.roleName(role.getRoleName())
			.build();
	}

}

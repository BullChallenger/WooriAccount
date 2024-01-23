package io.woori.account.wooriaccount.customer.domain.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.woori.account.wooriaccount.customer.domain.entity.Customer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CacheCustomerDTO implements Serializable {

	private String customerId;
	private String customerName;
	private String customerPhone;
	private String customerEmail;
	private String customerPwd;

	public CacheCustomerDTO(String jsonString) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		CacheCustomerDTO dto = objectMapper.readValue(jsonString, CacheCustomerDTO.class);

		this.customerId = dto.customerId;
		this.customerName = dto.customerName;
		this.customerPhone = dto.customerPhone;
		this.customerEmail = dto.customerEmail;
		this.customerPwd = dto.customerPwd;
	}

	@Builder
	public CacheCustomerDTO(String customerId, String customerName, String customerPhone, String customerEmail,
		String customerPwd) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerPhone = customerPhone;
		this.customerEmail = customerEmail;
		this.customerPwd = customerPwd;
	}

	public static CacheCustomerDTO from(Customer customer) {
		return CacheCustomerDTO.builder()
			.customerId(String.valueOf(customer.getCustomerId()))
			.customerName(customer.getCustomerName())
			.customerPhone(customer.getCustomerPhone())
			.customerEmail(customer.getCustomerEmail())
			.customerPwd(customer.getCustomerPwd())
			.build();
	}

}

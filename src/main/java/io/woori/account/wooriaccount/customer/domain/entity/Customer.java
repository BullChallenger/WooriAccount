package io.woori.account.wooriaccount.customer.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import io.woori.account.wooriaccount.common.domain.entity.BaseEntity;
import io.woori.account.wooriaccount.customer.domain.dto.SignUpRequestDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Table(name = "customers")
@Where(clause = "IS_DELETED = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Setter
public class Customer extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;  //고객 ID

	@Column(nullable = false)
	private String customerName;  // 고객 이름

	@Column(nullable = false)
	private String customerPhone;  // 고객 전화번호

	@Column(nullable = false)
	private String customerEmail;  // 고객 이메일

	@Column(nullable = false)
	private String customerPwd;  //고객 비밀번호

	public Customer(String customerName, String customerPhone, String customerEmail, String customerPwd) {
		this.customerName = customerName;
		this.customerPhone = customerPhone;
		this.customerEmail = customerEmail;
		this.customerPwd = customerPwd;
	}

	public static Customer createCustomer(SignUpRequestDTO dto, String encodePwd) {
		return Customer.builder()
			.customerName(dto.getCustomerName())
			.customerPhone(dto.getCustomerPhone())
			.customerEmail(dto.getCustomerEmail())
			.customerPwd(encodePwd)
			.build();
	}

}

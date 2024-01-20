package io.woori.account.wooriaccount.role.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import io.woori.account.wooriaccount.customer.domain.entity.Customer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder

@Entity
@Table(name = "customer_roles")
public class CustomerRole {

	@EmbeddedId
	private Pk pk;

	@MapsId("customerId")
	@JoinColumn(name = "customer_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Customer customer;

	@MapsId("roleId")
	@JoinColumn(name = "role_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Role role;

	@Embeddable
	@EqualsAndHashCode
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	@Getter
	public static class Pk implements Serializable {

		@Column(name = "customer_id")
		private Long customerId;

		@Column(name = "role_id")
		private Byte roleId;

	}

	public static CustomerRole createCustomerRole(Customer customer, Role role){
		return CustomerRole.builder()
				.pk(Pk.builder()
						.roleId(role.getRoleId())
						.customerId(customer.getCustomerId())
						.build())
				.customer(customer)
				.role(role)
				.build();
	}
}

package io.woori.account.wooriaccount.security.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.woori.account.wooriaccount.customer.domain.dto.CacheCustomerDTO;
import io.woori.account.wooriaccount.customer.domain.entity.Customer;


/* UserDetails를 직접 구현한 클래스입니다.
 * */

public class CustomUserDetails implements UserDetails {
	private String customerId;
	private String customerEmail;
	private String customerPwd;
	private boolean isDeleted;
	private Collection<GrantedAuthority> authorities;

	//TODO 2: 해당 부분 역시 role 관련 작업 후 수정 예정
	public CustomUserDetails(Customer customer) {
		//this.authorities = customer.getRoles();
		this.customerEmail = customer.getCustomerEmail();
		this.customerPwd = customer.getCustomerPwd();
		this.customerId = String.valueOf(customer.getCustomerId());

	}

	public CustomUserDetails(CacheCustomerDTO dto) {
		this.customerId = String.valueOf(dto.getCustomerId());
		this.customerEmail = dto.getCustomerEmail();
		this.customerPwd = dto.getCustomerPwd();
	}

	//TODO 1: 해당 부분을 새로 생성해주기 위해서 user 정보에 role 정보 추가 요청 List<String> roles 등
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {

		return this.customerPwd;
	}

	@Override
	public String getUsername() {

		return this.customerEmail;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.isDeleted;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.isDeleted;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
}

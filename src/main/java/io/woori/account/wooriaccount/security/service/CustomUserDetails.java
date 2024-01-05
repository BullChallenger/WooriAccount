package io.woori.account.wooriaccount.security.service;

import io.woori.account.wooriaccount.domain.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


/* UserDetails를 직접 구현한 클래스입니다.
* */


public class CustomUserDetails implements UserDetails {
    private String customerId;
    private String customerEmail;
    private String customerPwd;
    private boolean isDeleted;
    private Collection<GrantedAuthority> authorities;

    public CustomUserDetails(Customer customer){
        //this.authorities = customer.getRoles();

        this.customerEmail = customer.getCustomerEmail();
        this.customerPwd = customer.getCustomerPwd();
        this.customerId = String.valueOf(customer.getCustomerId());

    }
    //TODO: 해당 부분을 새로 생성해주기 위해서 user 정보에 role 정보 추가 요청 List<String> roles 등
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

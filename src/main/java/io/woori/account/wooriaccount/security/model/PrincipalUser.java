package io.woori.account.wooriaccount.security.model;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;

@Getter
public class PrincipalUser implements OidcUser, UserDetails, OAuth2User {

	private final ProviderUser providerUser;

	public PrincipalUser(ProviderUser providerUser) {
		this.providerUser = providerUser;
	}

	@Override
	public String getUsername() {
		return providerUser.getUsername();
	}

	@Override
	public String getPassword() {
		return providerUser.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Map<String, Object> getClaims() {
		return providerUser.getAttributes();
	}

	@Override
	public OidcUserInfo getUserInfo() {
		return null;
	}

	@Override
	public OidcIdToken getIdToken() {
		return null;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return providerUser.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return providerUser.getAuthorities();
	}

	@Override
	public String getName() {
		return providerUser.getUsername();
	}

}

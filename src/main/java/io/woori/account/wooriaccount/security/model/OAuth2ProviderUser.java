package io.woori.account.wooriaccount.security.model;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class OAuth2ProviderUser implements ProviderUser {

	private final Map<String, Object> attributes;
	private final OAuth2User oAuth2User;
	private final ClientRegistration clientRegistration;

	@Override
	public String getPassword() {
		return UUID.randomUUID().toString();
	}

	@Override
	public String getEmail() {
		return (String)attributes.get("email");
	}

	@Override
	public String getProvider() {
		return clientRegistration.getRegistrationId();
	}

	@Override
	public List<? extends GrantedAuthority> getAuthorities() {
		return oAuth2User.getAuthorities().stream().map(authority ->
			new SimpleGrantedAuthority(authority.getAuthority())).collect(Collectors.toList());
	}

}

package io.woori.account.wooriaccount.security.common;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import io.woori.account.wooriaccount.customer.domain.entity.Customer;
import lombok.Getter;

@Getter
public class ProviderUserRequest {

	private final ClientRegistration clientRegistration;
	private final OAuth2User oAuth2User;
	private final Customer customer;

	public ProviderUserRequest(ClientRegistration clientRegistration, OAuth2User oAuth2User, Customer customer) {
		this.clientRegistration = clientRegistration;
		this.oAuth2User = oAuth2User;
		this.customer = customer;
	}

	public ProviderUserRequest(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
		this(clientRegistration, oAuth2User, null);
	}

	public ProviderUserRequest(Customer customer) {
		this(null, null, customer);
	}
}

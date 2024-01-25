package io.woori.account.wooriaccount.security.model;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class GoogleUser extends OAuth2ProviderUser {
	public GoogleUser(Attributes mainAttributes, OAuth2User oAuth2User, ClientRegistration clientRegistration) {
		super(mainAttributes.getMainAttributes(), oAuth2User, clientRegistration);
	}

	@Override
	public String getId() {
		return (String)getAttributes().get("sub");
	}

	@Override
	public String getUsername() {
		return (String)getAttributes().get("name");
	}

	@Override
	public String getPicture() {
		return null;
	}

}

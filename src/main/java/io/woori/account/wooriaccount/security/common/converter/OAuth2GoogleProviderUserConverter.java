package io.woori.account.wooriaccount.security.common.converter;

import static io.woori.account.wooriaccount.security.common.constant.SocialType.*;

import io.woori.account.wooriaccount.security.common.ProviderUserRequest;
import io.woori.account.wooriaccount.security.common.utils.OAuth2Util;
import io.woori.account.wooriaccount.security.model.GoogleUser;
import io.woori.account.wooriaccount.security.model.ProviderUser;

public final class OAuth2GoogleProviderUserConverter
	implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {

	@Override
	public ProviderUser convert(ProviderUserRequest providerUserRequest) {

		if (!providerUserRequest.getClientRegistration().getRegistrationId().equals(GOOGLE.getSocialName())) {
			return null;
		}

		return new GoogleUser(
			OAuth2Util.getMainAttributes(providerUserRequest.getOAuth2User()),
			providerUserRequest.getOAuth2User(),
			providerUserRequest.getClientRegistration()
		);
	}

}
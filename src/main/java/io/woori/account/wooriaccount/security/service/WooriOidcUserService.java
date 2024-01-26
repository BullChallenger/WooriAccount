package io.woori.account.wooriaccount.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import io.woori.account.wooriaccount.customer.repository.jpa.CustomerRepository;
import io.woori.account.wooriaccount.role.repository.jpa.CustomerRoleRepository;
import io.woori.account.wooriaccount.role.repository.jpa.RoleRepository;
import io.woori.account.wooriaccount.security.common.ProviderUserRequest;
import io.woori.account.wooriaccount.security.common.converter.ProviderUserConverter;
import io.woori.account.wooriaccount.security.model.PrincipalUser;
import io.woori.account.wooriaccount.security.model.ProviderUser;

@Service
public class WooriOidcUserService extends AbstractOAuth2UserService
	implements OAuth2UserService<OidcUserRequest, OidcUser> {

	public WooriOidcUserService(PasswordEncoder passwordEncoder, CustomerRepository customerRepository,
		RoleRepository roleRepository,
		CustomerRoleRepository customerRoleRepository,
		ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter) {
		super(passwordEncoder, customerRepository, roleRepository, customerRoleRepository, providerUserConverter);
	}

	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		ClientRegistration clientRegistration =
			ClientRegistration.withClientRegistration(userRequest.getClientRegistration())
				.userNameAttributeName("sub")
				.build();

		OidcUserRequest oidcUserRequest = new OidcUserRequest(
			clientRegistration,
			userRequest.getAccessToken(),
			userRequest.getIdToken(),
			userRequest.getAdditionalParameters()
		);

		OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService = new OidcUserService();
		OidcUser oidcUser = oidcUserService.loadUser(oidcUserRequest);

		ProviderUserRequest providerUserRequest = new ProviderUserRequest(clientRegistration, oidcUser);
		ProviderUser providerUser = providerUser(providerUserRequest);

		super.saveIfNotPresent(providerUser, oidcUserRequest);

		return new PrincipalUser(providerUser);
	}

}


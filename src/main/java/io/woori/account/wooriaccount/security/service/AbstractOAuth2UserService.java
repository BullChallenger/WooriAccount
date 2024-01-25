package io.woori.account.wooriaccount.security.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;

import io.woori.account.wooriaccount.common.exception.CustomException;
import io.woori.account.wooriaccount.common.exception.ErrorCode;
import io.woori.account.wooriaccount.customer.domain.entity.Customer;
import io.woori.account.wooriaccount.customer.repository.jpa.CustomerRepository;
import io.woori.account.wooriaccount.role.domain.CustomerRole;
import io.woori.account.wooriaccount.role.domain.Role;
import io.woori.account.wooriaccount.role.repository.jpa.CustomerRoleRepository;
import io.woori.account.wooriaccount.role.repository.jpa.RoleRepository;
import io.woori.account.wooriaccount.security.common.ProviderUserRequest;
import io.woori.account.wooriaccount.security.common.converter.ProviderUserConverter;
import io.woori.account.wooriaccount.security.model.ProviderUser;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public abstract class AbstractOAuth2UserService {

	private final PasswordEncoder passwordEncoder;
	private final CustomerRepository customerRepository;
	private final RoleRepository roleRepository;
	private final CustomerRoleRepository customerRoleRepository;
	private final ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter;

	public void saveIfNotPresent(ProviderUser providerUser, OAuth2UserRequest oAuth2UserRequest) {
		Optional<Customer> opCustomer = customerRepository.findByCustomerEmail(providerUser.getEmail());
		if (opCustomer.isEmpty()) {
			ClientRegistration clientRegistration = oAuth2UserRequest.getClientRegistration();
			Customer customer = customerRepository.save(
				Customer.builder()
					.customerName(providerUser.getUsername())
					.customerEmail(providerUser.getEmail())
					.customerPhone("000-0000-0000")
					.customerPwd(passwordEncoder.encode(providerUser.getPassword()))
					.provider(clientRegistration.getRegistrationId())
					.build()
			);

			Optional<Role> op = roleRepository.findById(Byte.valueOf("2"));

			if (op.isEmpty()) {
				throw new CustomException(ErrorCode.NOT_FOUND_ROLE);
			}

			Role role = op.get();

			CustomerRole customerRole = CustomerRole.createCustomerRole(customer, role);

			customerRoleRepository.save(customerRole);
		}
	}

	public ProviderUser providerUser(ProviderUserRequest providerUserRequest) {
		return providerUserConverter.convert(providerUserRequest);
	}

}

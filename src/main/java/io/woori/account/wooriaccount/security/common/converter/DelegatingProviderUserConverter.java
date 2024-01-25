package io.woori.account.wooriaccount.security.common.converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import io.woori.account.wooriaccount.security.common.ProviderUserRequest;
import io.woori.account.wooriaccount.security.model.ProviderUser;

@Component
public class DelegatingProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {

	private final List<ProviderUserConverter<ProviderUserRequest, ProviderUser>> converters;

	public DelegatingProviderUserConverter(List<ProviderUserConverter<ProviderUserRequest, ProviderUser>> converters) {
		List<ProviderUserConverter<ProviderUserRequest, ProviderUser>> providerUserConverters = Arrays.asList(
			new OAuth2GoogleProviderUserConverter()
		);

		this.converters = Collections.unmodifiableList(new LinkedList<>(providerUserConverters));
	}

	@Nullable
	@Override
	public ProviderUser convert(ProviderUserRequest providerUserRequest) {
		Assert.notNull(providerUserRequest, "providerUserRequest cannot be null");

		for (ProviderUserConverter<ProviderUserRequest, ProviderUser> converter : this.converters) {
			ProviderUser providerUser = converter.convert(providerUserRequest);
			if (providerUser != null) {
				return providerUser;
			}
		}
		
		return null;
	}

}

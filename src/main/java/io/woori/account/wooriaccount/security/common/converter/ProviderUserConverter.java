package io.woori.account.wooriaccount.security.common.converter;

public interface ProviderUserConverter<T, R> {
	R convert(T t);
}
package io.woori.account.wooriaccount.security.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialType {

	GOOGLE("google");

	private final String socialName;

}

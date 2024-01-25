package io.woori.account.wooriaccount.security.model;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Attributes {

	private Map<String, Object> mainAttributes;
	private Map<String, Object> subAttributes;
	private Map<String, Object> otherAttributes;

	@Builder
	public Attributes(Map<String, Object> mainAttributes, Map<String, Object> subAttributes,
		Map<String, Object> otherAttributes) {
		this.mainAttributes = mainAttributes;
		this.subAttributes = subAttributes;
		this.otherAttributes = otherAttributes;
	}

}

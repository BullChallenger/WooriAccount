package io.woori.account.wooriaccount.dummy;

import io.woori.account.wooriaccount.domain.entity.Customer;
import io.woori.account.wooriaccount.dto.user.SignUpRequestDTO;

public class DummyCustomer {

	public static Customer dummy(SignUpRequestDTO dto) {
	
		return Customer.of(dto);
	}


}

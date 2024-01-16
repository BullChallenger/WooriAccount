package io.woori.account.wooriaccount.dummy;

import io.woori.account.wooriaccount.customer.domain.entity.Customer;
import io.woori.account.wooriaccount.customer.domain.dto.SignUpRequestDTO;

public class DummyCustomer {

	public static Customer dummy(SignUpRequestDTO dto) {
	
		return Customer.of(dto);
	}


}

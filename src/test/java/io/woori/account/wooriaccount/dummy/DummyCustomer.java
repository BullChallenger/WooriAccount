package io.woori.account.wooriaccount.dummy;

import io.woori.account.wooriaccount.customer.domain.dto.SignUpRequestDTO;
import io.woori.account.wooriaccount.customer.domain.entity.Customer;

public class DummyCustomer {

	public static Customer dummy(SignUpRequestDTO dto, String pwd) {

		return Customer.createCustomer(dto, pwd);
	}

}

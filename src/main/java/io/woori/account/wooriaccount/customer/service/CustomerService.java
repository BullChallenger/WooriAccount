package io.woori.account.wooriaccount.customer.service;

import io.woori.account.wooriaccount.customer.domain.dto.CustomerUpdateDTO;
import io.woori.account.wooriaccount.customer.domain.dto.LoginRequestDTO;
import io.woori.account.wooriaccount.customer.domain.dto.LoginResponseDTO;
import io.woori.account.wooriaccount.customer.domain.dto.SignUpRequestDTO;

public interface CustomerService {

	String signUp(SignUpRequestDTO dto);

	String update(CustomerUpdateDTO dto);

	String delete(Long id);

	LoginResponseDTO login(LoginRequestDTO dto);
}

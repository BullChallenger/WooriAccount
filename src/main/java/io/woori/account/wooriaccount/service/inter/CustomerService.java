package io.woori.account.wooriaccount.service.inter;

import io.woori.account.wooriaccount.dto.customer.CustomerUpdateDTO;
import io.woori.account.wooriaccount.dto.customer.LoginRequestDTO;
import io.woori.account.wooriaccount.dto.customer.LoginResponseDTO;
import io.woori.account.wooriaccount.dto.customer.SignUpRequestDTO;

public interface CustomerService {

    String signUp(SignUpRequestDTO dto);

    String update(CustomerUpdateDTO dto);

    String delete(Long id);

    LoginResponseDTO login(LoginRequestDTO dto);
}

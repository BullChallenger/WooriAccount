package io.woori.account.wooriaccount.service.inter;

import io.woori.account.wooriaccount.dto.user.CustomerUpdateDTO;
import io.woori.account.wooriaccount.dto.user.LoginRequestDTO;
import io.woori.account.wooriaccount.dto.user.LoginResponseDTO;
import io.woori.account.wooriaccount.dto.user.SignUpRequestDTO;

public interface CustomerService {

    String signUp(SignUpRequestDTO dto);

    String update(CustomerUpdateDTO dto);

    String delete(Long id);

    LoginResponseDTO login(LoginRequestDTO dto);
}

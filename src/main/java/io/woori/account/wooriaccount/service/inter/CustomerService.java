package io.woori.account.wooriaccount.service.inter;

import io.woori.account.wooriaccount.dto.user.LoginRequestDTO;
import io.woori.account.wooriaccount.dto.user.LoginResponseDTO;
import io.woori.account.wooriaccount.dto.user.SignUpRequestDTO;

public interface CustomerService {

    public String signUp(SignUpRequestDTO dto);

    public LoginResponseDTO login(LoginRequestDTO dto);
}

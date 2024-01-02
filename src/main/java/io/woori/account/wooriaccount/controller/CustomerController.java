package io.woori.account.wooriaccount.controller;

import io.woori.account.wooriaccount.dto.user.LoginRequestDTO;
import io.woori.account.wooriaccount.dto.user.LoginResponseDTO;
import io.woori.account.wooriaccount.dto.user.SignUpRequestDTO;
import io.woori.account.wooriaccount.service.inter.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return customerService.login(loginRequestDTO);
    }

    public String signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        return customerService.signUp(signUpRequestDTO);
    }

    public String delete(@RequestParam(name = "customerId") Long customerId) {
        return customerService.delete(customerId);
    }

}

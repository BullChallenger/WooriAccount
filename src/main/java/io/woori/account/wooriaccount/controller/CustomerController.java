package io.woori.account.wooriaccount.controller;

import io.woori.account.wooriaccount.dto.customer.CustomerUpdateDTO;
import io.woori.account.wooriaccount.dto.customer.LoginRequestDTO;
import io.woori.account.wooriaccount.dto.customer.LoginResponseDTO;
import io.woori.account.wooriaccount.dto.customer.SignUpRequestDTO;
import io.woori.account.wooriaccount.service.inter.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO , HttpSession session) {
        LoginResponseDTO loginResponseDTO = customerService.login(loginRequestDTO);

        session.setAttribute("customerId", loginResponseDTO);

        return loginResponseDTO;
    }

    @PostMapping("/signUp")
    public String signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        return customerService.signUp(signUpRequestDTO);
    }


    @PostMapping("/delete")
    public String delete(@RequestParam(name = "customerId") Long customerId) {
        return customerService.delete(customerId);
    }

    @PostMapping("/update")
    public String update(@RequestBody CustomerUpdateDTO customerUpdateDTO) {
        return customerService.update(customerUpdateDTO);
    }
}

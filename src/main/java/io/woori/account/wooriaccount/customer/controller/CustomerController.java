package io.woori.account.wooriaccount.customer.controller;

import javax.servlet.http.HttpSession;

import io.woori.account.wooriaccount.common.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.woori.account.wooriaccount.customer.domain.dto.CustomerUpdateDTO;
import io.woori.account.wooriaccount.customer.domain.dto.LoginRequestDTO;
import io.woori.account.wooriaccount.customer.domain.dto.LoginResponseDTO;
import io.woori.account.wooriaccount.customer.domain.dto.SignUpRequestDTO;
import io.woori.account.wooriaccount.customer.domain.dto.SignUpResponseDTO;
import io.woori.account.wooriaccount.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/customers")
public class CustomerController {

	private final CustomerService customerService;

	@PostMapping("/login")
	public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO, HttpSession session) {
		LoginResponseDTO loginResponseDTO = customerService.login(loginRequestDTO);

		session.setAttribute("customerId", loginResponseDTO);

		return loginResponseDTO;
	}

	@PostMapping("/signUp")
	public ResponseEntity<ResponseDTO<SignUpResponseDTO>> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {

		return ResponseEntity.ok(ResponseDTO.of(HttpStatus.CREATED, "회원 가입에 성공했습니다.", customerService.signUp(signUpRequestDTO)));



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

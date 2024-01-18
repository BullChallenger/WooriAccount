package io.woori.account.wooriaccount.customer.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.woori.account.wooriaccount.common.exception.CustomException;
import io.woori.account.wooriaccount.common.exception.ErrorCode;
import io.woori.account.wooriaccount.customer.domain.dto.CustomerUpdateDTO;
import io.woori.account.wooriaccount.customer.domain.dto.LoginRequestDTO;
import io.woori.account.wooriaccount.customer.domain.dto.LoginResponseDTO;
import io.woori.account.wooriaccount.customer.domain.dto.SignUpRequestDTO;
import io.woori.account.wooriaccount.customer.domain.entity.Customer;
import io.woori.account.wooriaccount.customer.repository.jpa.CustomerRepository;
import io.woori.account.wooriaccount.encryption.EncryptHelper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final EncryptHelper encryptHelper;

	@Override
	public String signUp(SignUpRequestDTO dto) {
		// 이메일 중복 여부 체크
		customerRepository.findByCustomerEmail(dto.getCustomerEmail()).ifPresent(
			existingCustomer -> {
				throw new CustomException(ErrorCode.DUPLICATE_CUSTOMER);
			}
		);

		String encrypted = encryptHelper.encrypt(dto.getCustomerPwd());
		dto.setCustomerPwd(encrypted);

		Customer customer = Customer.of(dto);
		customerRepository.save(customer);

		return "success";
	}

	@Override
	public String update(CustomerUpdateDTO dto) {

		Customer customer = customerRepository.findById(dto.getCustomerId()).get();

		String encrypted = encryptHelper.encrypt(dto.getCustomerPwd());
		dto.setCustomerPwd(encrypted);

		customer.setCustomerName(dto.getCustomerName());
		customer.setCustomerEmail(dto.getCustomerEmail());
		customer.setCustomerPhone(dto.getCustomerPhone());
		customer.setCustomerPwd(dto.getCustomerPwd());

		return "success";
	}

	@Override
	public String delete(Long id) {
		customerRepository.deleteById(id);
		return "success";
	}

	@Override
	public LoginResponseDTO login(LoginRequestDTO dto) {
		// 해당하는 회원이 있는지 체크
		Customer find = customerRepository.findByCustomerEmail(dto.getEmail())
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_Customer_Login));

		// 비밀번호 일치여부 체크
		if (!encryptHelper.isMatch(dto.getPwd(), find.getCustomerPwd())) {
			throw new CustomException(ErrorCode.INVALID_Customer_Password);
		}

		// 로그인 성공 시
		return new LoginResponseDTO(find.getCustomerId(), find.getCustomerName(), find.getCustomerPhone(),
			find.getCustomerEmail());
	}

}

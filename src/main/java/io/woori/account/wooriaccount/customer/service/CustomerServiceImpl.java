package io.woori.account.wooriaccount.customer.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.woori.account.wooriaccount.common.exception.CustomException;
import io.woori.account.wooriaccount.common.exception.ErrorCode;
import io.woori.account.wooriaccount.customer.domain.dto.CustomerUpdateDTO;
import io.woori.account.wooriaccount.customer.domain.dto.LoginRequestDTO;
import io.woori.account.wooriaccount.customer.domain.dto.LoginResponseDTO;
import io.woori.account.wooriaccount.customer.domain.dto.SignUpRequestDTO;
import io.woori.account.wooriaccount.customer.domain.dto.SignUpResponseDTO;
import io.woori.account.wooriaccount.customer.domain.entity.Customer;
import io.woori.account.wooriaccount.customer.repository.jpa.CustomerRepository;
import io.woori.account.wooriaccount.role.domain.CustomerRole;
import io.woori.account.wooriaccount.role.domain.CustomerRole.Pk;
import io.woori.account.wooriaccount.role.domain.Role;
import io.woori.account.wooriaccount.role.repository.jpa.CustomerRoleRepository;
import io.woori.account.wooriaccount.role.repository.jpa.RoleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	private final CustomerRoleRepository customerRoleRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder encoder;

	@Override
	public SignUpResponseDTO signUp(SignUpRequestDTO dto) {

		// 이메일 중복 여부 체크
		customerRepository.findByCustomerEmail(dto.getCustomerEmail()).ifPresent(
			existingCustomer -> {
				throw new CustomException(ErrorCode.DUPLICATE_CUSTOMER);
			}
		);

		Customer customer = Customer.of(dto, encoder.encode(dto.getCustomerPwd()));
		customerRepository.save(customer);

		// 1번 ROLE_ADMIN , 2번 ROLE_USER 저장후 사용하셈
		Optional<Role> op = roleRepository.findById(2L);

		if (op.isEmpty()) {
			throw new CustomException(ErrorCode.NOT_FOUND_ROLE);
		}

		Role role = op.get();

		CustomerRole customerRole = CustomerRole.builder()
			.pk(Pk.builder()
				.roleId(role.getRoleId())
				.customerId(customer.getCustomerId())
				.build())
			.customer(customer)
			.role(role)
			.build();

		customerRoleRepository.save(customerRole);

		return SignUpResponseDTO.fromEntity(customer, role);

	}

	@Override
	public String update(CustomerUpdateDTO dto) {

		Optional<Customer> op = customerRepository.findById(dto.getCustomerId());

		if (op.isEmpty()) {
			throw new CustomException(ErrorCode.NOT_FOUND_CUSTOMER);
		}

		Customer customer = op.get();

		String encrypted = encoder.encode(dto.getCustomerPwd());
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
		if (!encoder.matches(dto.getPwd(), find.getCustomerPwd())) {
			throw new CustomException(ErrorCode.INVALID_Customer_Password);
		}

		// 로그인 성공 시
		return new LoginResponseDTO(find.getCustomerId(), find.getCustomerName(), find.getCustomerPhone(),
			find.getCustomerEmail());
	}

}

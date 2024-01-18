package io.woori.account.wooriaccount.service;

import io.woori.account.wooriaccount.domain.entity.Customer;
import io.woori.account.wooriaccount.domain.entity.CustomerRole;
import io.woori.account.wooriaccount.domain.entity.Role;
import io.woori.account.wooriaccount.dto.customer.CustomerUpdateDTO;
import io.woori.account.wooriaccount.dto.customer.LoginRequestDTO;
import io.woori.account.wooriaccount.dto.customer.LoginResponseDTO;
import io.woori.account.wooriaccount.dto.customer.SignUpRequestDTO;
import io.woori.account.wooriaccount.encryption.EncryptHelper;
import io.woori.account.wooriaccount.exception.CustomException;
import io.woori.account.wooriaccount.exception.ErrorCode;
import io.woori.account.wooriaccount.repository.jpa.CustomerRepository;
import io.woori.account.wooriaccount.repository.jpa.CustomerRoleRepository;
import io.woori.account.wooriaccount.repository.jpa.RoleRepository;
import io.woori.account.wooriaccount.service.inter.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerRoleRepository customerRoleRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;



    @Transactional
    @Override
    public String signUp(SignUpRequestDTO dto) {
        // 이메일 중복 여부 체크
        customerRepository.findByCustomerEmail(dto.getCustomerEmail()).ifPresent(
                existingCustomer -> {
                    throw new CustomException(ErrorCode.DUPLICATE_CUSTOMER);
                }
        );

        // customer = Customer.of(dto, encoder.encode(dto.getCustomerPwd()));
        Customer customer = Customer.builder()
                .customerName(dto.getCustomerName())
                .customerPhone(dto.getCustomerPhone())
                .customerEmail(dto.getCustomerEmail())
                .customerPwd(encoder.encode(dto.getCustomerPwd()))
                .build();

        // 이부분이 먼저 선행이 되어야 오류가 나지 않습니다.
        customerRepository.save(customer);



        //해당 정보를 사용하기 위해서 Role 테이블을 먼저 1- ROLE_ADMIN, 2 - ROLE_USER 로 채워서 사용해주삼 ~
        Role userRole = roleRepository.findById(2L);

        log.info("customer email {}", customer.getCustomerEmail());
        log.info("user role {}", userRole.getRoleName());

        customerRoleRepository.save(
                CustomerRole.builder()
                        .customerRolePk(CustomerRole.CustomerRolePk.builder()
                                .customerId(customer.getCustomerId())
                                .roleId(userRole.getRoleId())
                                .build())
                        .customer(customer)
                        .role(userRole)
                        .build());




        return "success";
    }


    @Transactional
    @Override
    public String update(CustomerUpdateDTO dto) {

        Customer customer = customerRepository.findById(dto.getCustomerId()).get();

        String encrypted = encoder.encode(dto.getCustomerPwd());
        dto.setCustomerPwd(encrypted);

        customer.setCustomerName(dto.getCustomerName());
        customer.setCustomerEmail(dto.getCustomerEmail());
        customer.setCustomerPhone(dto.getCustomerPhone());
        customer.setCustomerPwd(dto.getCustomerPwd());

        return "success";
    }


    @Transactional
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
        return new LoginResponseDTO(find.getCustomerId(), find.getCustomerName(), find.getCustomerPhone(), find.getCustomerEmail());
    }



}

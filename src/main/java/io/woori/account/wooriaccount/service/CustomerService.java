package io.woori.account.wooriaccount.service;

import io.woori.account.wooriaccount.domain.entity.Customer;
import io.woori.account.wooriaccount.dto.user.SignUpRequestDTO;
import io.woori.account.wooriaccount.encryption.EncryptHelper;
import io.woori.account.wooriaccount.exception.CustomException;
import io.woori.account.wooriaccount.exception.ErrorCode;
import io.woori.account.wooriaccount.repository.jpa.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final EncryptHelper encryptHelper;

    @Transactional
    public String signUp(SignUpRequestDTO dto) {
        //이메일 중복여부 체크
        if(customerRepository.findByCustomerEmail(dto.getCustomerEmail()) != null) {
            throw new CustomException(ErrorCode.DUPLICATE_CUSTOMER);
        }

        String encrypted = encryptHelper.encrypt(dto.getCustomerPwd());
        dto.setCustomerPwd(encrypted);

        Customer customer = Customer.of(dto);
        customerRepository.save(customer);

        return "success";
    }



}

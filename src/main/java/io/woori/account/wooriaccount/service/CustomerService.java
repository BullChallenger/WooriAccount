package io.woori.account.wooriaccount.service;

import io.woori.account.wooriaccount.domain.entity.Customer;
import io.woori.account.wooriaccount.dto.user.LoginRequestDTO;
import io.woori.account.wooriaccount.dto.user.LoginResponseDTO;
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

    public LoginResponseDTO login(LoginRequestDTO dto) {
        //해당하는 회원이 있는지 체크
        Customer find = customerRepository.findByCustomerEmail(dto.getEmail());

        if(find == null){
            throw new CustomException(ErrorCode.INVALID_Customer_Login);
        }

        String encrypted = find.getCustomerPwd(); // pwd 암호화되어 저장되어 있음
        boolean result = encryptHelper.isMatch(dto.getPwd(), encrypted);

        //비밀번호 일치여부 체크
        if(!result){
            throw new CustomException(ErrorCode.INVALID_Customer_Password);
        }

        LoginResponseDTO loginResponseDTO =
                new LoginResponseDTO(find.getCustomerId(),find.getCustomerName(),find.getCustomerPhone(),find.getCustomerEmail());

        return loginResponseDTO;
    }



}

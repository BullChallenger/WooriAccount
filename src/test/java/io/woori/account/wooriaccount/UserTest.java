package io.woori.account.wooriaccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import io.woori.account.wooriaccount.dto.customer.LoginRequestDTO;
import io.woori.account.wooriaccount.dto.customer.LoginResponseDTO;
import io.woori.account.wooriaccount.dto.customer.SignUpRequestDTO;
import io.woori.account.wooriaccount.exception.CustomException;
import io.woori.account.wooriaccount.service.CustomerServiceImpl;

@SpringBootTest
@Transactional
public class UserTest {

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @Test
    @Rollback
    public void 회원가입(){
        //given
        SignUpRequestDTO dto = new SignUpRequestDTO("봉", "010-1234-1234", "abc@naver.com", "1234");
        //when
        String result = customerServiceImpl.signUp(dto);
        //then
        assertEquals("success", result);
    }

    @Test
    @Rollback
    public void 로그인(){
        SignUpRequestDTO dto = new SignUpRequestDTO("봉", "010-1234-1234", "abc@naver.com", "1234");
        customerServiceImpl.signUp(dto);

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("abc@naver.com", "1234");
        LoginResponseDTO loginResponseDTO = customerServiceImpl.login(loginRequestDTO);
        System.out.println(loginResponseDTO.getCustomerEmail());
    }

    @Test
    @Rollback
    public void 로그인시도_오류() throws Exception{
        //given
        SignUpRequestDTO dto = new SignUpRequestDTO("봉", "010-1234-1234", "abc@naver.com", "1234");
        customerServiceImpl.signUp(dto);

        // when
        CustomException exception = assertThrows(CustomException.class, () -> {
            LoginRequestDTO loginRequestDTO = new LoginRequestDTO("def@naver", "1234"); // 잘못된 이메일로 로그인 시도
            customerServiceImpl.login(loginRequestDTO);
        });

        // then
        assertEquals("존재하지 않는 고객 정보입니다.", exception.getMessage());
    }

    @Test
    @Rollback
    public void 중복회원가입_오류(){
        SignUpRequestDTO dto1 = new SignUpRequestDTO("봉", "010-1234-1234", "abc@naver.com", "1234");
        SignUpRequestDTO dto2 = new SignUpRequestDTO("섭", "010-5678-5678", "abc@naver.com", "5678");

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerServiceImpl.signUp(dto1);
            customerServiceImpl.signUp(dto2);
        });

        // then
        assertEquals("중복된 이메일입니다.", exception.getMessage());
    }
}

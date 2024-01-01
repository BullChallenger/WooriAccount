package io.woori.account.wooriaccount;

import io.woori.account.wooriaccount.dto.user.LoginRequestDTO;
import io.woori.account.wooriaccount.dto.user.LoginResponseDTO;
import io.woori.account.wooriaccount.dto.user.SignUpRequestDTO;
import io.woori.account.wooriaccount.exception.CustomException;
import io.woori.account.wooriaccount.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void 회원가입(){
        //given
        SignUpRequestDTO dto = new SignUpRequestDTO("봉", "010-1234-1234", "abc@naver.com", "1234");
        //when
        String result = customerService.signUp(dto);
        //then
        assertEquals("success",result);
    }

    @Test
    public void 로그인(){
        SignUpRequestDTO dto = new SignUpRequestDTO("봉", "010-1234-1234", "abc@naver.com", "1234");
        customerService.signUp(dto);

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("abc@naver.com", "1234");
        LoginResponseDTO loginResponseDTO =  customerService.login(loginRequestDTO);
        System.out.println(loginResponseDTO.getCustomerEmail());

    }

    @Test
    public void 로그인시도_오류() throws Exception{
        //given
        SignUpRequestDTO dto = new SignUpRequestDTO("봉", "010-1234-1234", "abc@naver.com", "1234");
        customerService.signUp(dto);

        // when
        RuntimeException exception = assertThrows(CustomException.class, () -> {
            LoginRequestDTO loginRequestDTO = new LoginRequestDTO("def@naver", "1234"); // 잘못된 이메일로 로그인 시도
            customerService.login(loginRequestDTO);
        });

        // then
        assertEquals("존재하지 않는 고객 정보입니다.", exception.getMessage());
    }
    @Test
    public void 중복회원가입_오류(){
        SignUpRequestDTO dto1 = new SignUpRequestDTO("봉", "010-1234-1234", "abc@naver.com", "1234");
        SignUpRequestDTO dto2 = new SignUpRequestDTO("섭", "010-5678-5678", "abc@naver.com", "5678");

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.signUp(dto1);
            customerService.signUp(dto2);
        });

        // then
        assertEquals("중복된 이메일입니다.", exception.getMessage());
    }


}

package io.woori.account.wooriaccount;

import io.woori.account.wooriaccount.dto.user.SignUpRequestDTO;
import io.woori.account.wooriaccount.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void signUp(){
        //given
        SignUpRequestDTO dto = new SignUpRequestDTO("봉", "010-1234-1234", "abc@naver.com", "1234");
        //when
        String result = customerService.signUp(dto);
        //then
        assertEquals("success",result);
    }


}

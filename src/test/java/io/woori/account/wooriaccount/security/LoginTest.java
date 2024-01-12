package io.woori.account.wooriaccount.security;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;



    @Test
    @WithMockUser(username = "testUser", password = "testPassword", roles = "USER")
    public void loginWithMockUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/login")
                        .param("email", "testUser")
                        .param("pwd", "testPassword"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = "testUser", password = "fail", roles = "USER")
    public void loginWithMockUser_fail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/login")
                        .param("email", "testUser")
                        .param("pwd", "testPassword"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/customer/login?error"));

    }


//    @Test
//    @DisplayName("로그인 성공")
//    @WithMockUser
//    public void login_success() throws Exception{
//        Customer customer;
//
//        //회원가입
//        SignUpRequestDTO dto = new SignUpRequestDTO("test name","01011111111" , "test@test.com", "test pw");
//        customer = DummyCustomer.dummy(dto);
//
//        String email = customer.getCustomerEmail();
//        String pwd = customer.getCustomerPwd();
//
//        mockMvc.perform(formLogin("/customer/login").user(email).password(pwd))
//                .andDo(print())
//                //.andExpect(authenticated())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/"));
//    }


}

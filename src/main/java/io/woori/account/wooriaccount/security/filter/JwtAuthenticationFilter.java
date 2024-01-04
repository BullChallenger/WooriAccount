package io.woori.account.wooriaccount.security.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/*
* 로그인 폼에서 넘어온 값을 이용해 인증을 진행하는 필터입니다
* 해당 필터는 UsernamePasswordAuthenticationFiler를 상속받아 사용하며 이를 대체합니다.
*
* @author : hwiju yeom
* @
* */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    /*
    * 해당 메서드는 처음 로그인 요청이 들어와 id값과 password 값이 넘어올 때 동작합니다.
    * AuthenticationToken 객체를 생성해 AuthenticationManager 해당 인증 객체를 넘겨줍니다.
    * */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {


        String loginId = request.getParameter("loginId");
        String pwd = request.getParameter("pwd");

        if (loginId.isBlank() || loginId.isEmpty() || pwd.isBlank() || pwd.isEmpty()){
            throw new BadCredentialsException("로그인 아이디와 비밀번호는 빈값이 들어올 수 없습니다.");
        }

        log.info("jwtAuthenticationFilter : attemptAuthentication");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, pwd);

        authenticationManager.authenticate(authenticationToken);

        return authenticationToken;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("jwtAuthenticationFilter : successfulAuthentication");


        super.successfulAuthentication(request, response, chain, authResult);
    }


}

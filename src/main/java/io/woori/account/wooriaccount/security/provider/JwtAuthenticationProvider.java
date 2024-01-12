package io.woori.account.wooriaccount.security.provider;

import io.woori.account.wooriaccount.security.service.CustomUserDetails;
import io.woori.account.wooriaccount.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/*
* 실제 인증 과정을 수행하는 곳입니다.
* 입력한 값을 가져오고, DB에서 실제 저장된 데이터와의 값을 비교하는 작업을 이곳에서 진행합니다.
* */
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {


    private final CustomUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder encoder;

    /*
    * 해당 작업에서는 UserService에서 가져오는 UserDetails를 기준으로 로그인 정보 매칭을 확인합니다.
    * */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.info("JwtAuthenticationProvider : authenticate");

        String email = authentication.getName();
        String principal = (String)authentication.getCredentials();

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        log.info("{}, {}", userDetails.getUsername(),userDetails.getPassword());
        log.info(principal);

        if (userDetails.getUsername().equals(email) && encoder.matches(principal, userDetails.getPassword())){

            log.info("JwtAuthenticationProvider : authenticate, {} ", userDetails.getUsername());

            // TODO 3: 해당 부분 getAuthorities 값 설정 없어서 이부분도 수정 예정
            return UsernamePasswordAuthenticationToken.authenticated(userDetails.getUsername(), "", userDetails.getAuthorities());

        }


        throw new BadCredentialsException("해당 로그인 정보가 매칭되지 않습니다. 로그인 비밀번호를 다시 확인해주세요");
    }



    /*
    * Authentication 객체 유형 확인에 사용합니다.
    * */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }


}

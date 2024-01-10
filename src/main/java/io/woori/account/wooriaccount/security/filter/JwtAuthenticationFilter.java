package io.woori.account.wooriaccount.security.filter;

import io.woori.account.wooriaccount.security.utils.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


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
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, Object> redisTemplate;



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


        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(loginId, pwd);

        authenticationManager.authenticate(authenticationToken);

        return authenticationToken;
    }


    /*
    * 해당 메서드는 security 내부의 과정을 끝내고 성공적으로 인증을 한 경우에 동작합니다.
    * 이때 jwt를 생성해주고 이를 클라이언트에게 넘겨줍니다.
    * */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException, ServletException {
        log.info("jwtAuthenticationFilter : successfulAuthentication");

        String customEmail = (String) authentication.getPrincipal();
        List<String> authorities = extractAuthority(authentication);


        String accessToken = jwtProvider.createAccessToken(customEmail, authorities);
        String refreshToken = jwtProvider.createRefreshToken(customEmail, authorities);

        log.info("accessToken : ",accessToken);
        log.info("refreshToken : ",refreshToken);
        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("refreshToken", refreshToken);

        String randomId = generateRandomId(10);
        redisTemplate.opsForHash().put(randomId, "accessToken", accessToken);
        redisTemplate.opsForHash().put(randomId, "refreshToken", refreshToken);

        // TODO : 해당 부분 객체 넣고 싶으면 넣긔 ~ 회원 정보를 DTO로 만들어서 넣어도 됨 ~
        //redisTemplate.opsForHash().put(randomId, "customer", );

    }

    private static List<String> extractAuthority(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<GrantedAuthority> authorityList = new ArrayList<>(authorities);
        List<String> authority = new ArrayList<>();

        for (GrantedAuthority grantedAuthority : authorityList) {
            authority.add(grantedAuthority.getAuthority());
        }

        return authority;
    }


    private String generateRandomId(int length){
        StringBuilder sb = new StringBuilder(length);
        ThreadLocalRandom random = ThreadLocalRandom.current();

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i =0; i<length; i++) {
            int randomNum = random.nextInt(characters.length());
            sb.append(characters.charAt(randomNum));
        }
        return sb.toString();
    }


}

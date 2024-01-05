package io.woori.account.wooriaccount.security.filter;

import io.woori.account.wooriaccount.security.utils.JwtProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


/*
* OncePerRequestFilter는 시큐리티 필터가 아님!
*
* 해당 필터를 붙여서 모든 요청에 대해서 딱 한번만 실행되게 해서 인증이 성공한 경우라면 해당 jwt token을 가지고 로그인 유지와 같은 기능을 진행할 수 있게 합니다.
* */
public class JwtOncePerRequestFilter extends OncePerRequestFilter {
    private JwtProvider jwtProvider;

    /*
    * 토큰 유효성 검사를 진행하여 해당 토큰이 유효한 경우에 jwtProvider 내에서 해당 Authentication 객체를 꺼내옵니다.
    * 그 후에 해당 객체를 security context 안에 저장해 로그인 유지를 진행할 수 있게 합니다.
    * */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String removePrefixToken = removePrefixToken(request);

        // 유효한 토큰인지를 확인하고 security context에 해당 정보를 저장.
        if (Objects.nonNull(removePrefixToken) && jwtProvider.isValidToken(removePrefixToken)){

            Authentication authentication = jwtProvider.getAuthentication(removePrefixToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);

    }

    private static String removePrefixToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token.startsWith("Bearer ") && StringUtils.hasText(token)){
            //
            return token.substring(7);
        }
        return null;
    }
}

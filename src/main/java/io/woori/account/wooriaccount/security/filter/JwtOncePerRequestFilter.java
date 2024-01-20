package io.woori.account.wooriaccount.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.woori.account.wooriaccount.security.utils.CookieUtil;
import io.woori.account.wooriaccount.security.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/*
 * OncePerRequestFilter는 시큐리티 필터가 아님!
 *
 * 해당 필터를 붙여서 모든 요청에 대해서 딱 한번만 실행되게 해서 인증이 성공한 경우라면 해당 jwt token을 가지고 로그인 유지와 같은 기능을 진행할 수 있게 합니다.
 * */

@RequiredArgsConstructor
@Slf4j
public class JwtOncePerRequestFilter extends OncePerRequestFilter {
	private final JwtProvider jwtProvider;

	/*
	 * 토큰 유효성 검사를 진행하여 해당 토큰이 유효한 경우에 jwtProvider 내에서 해당 Authentication 객체를 꺼내옵니다.
	 * 그 후에 해당 객체를 security context 안에 저장해 로그인 유지를 진행할 수 있게 합니다.
	 * */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String removePrefixToken;

		log.info("request.getHeader {}", request.getHeader("Authorization"));

		if (request.getHeader("Authorization") == null || request.getHeader("Authorization").isEmpty()) {
			log.info("토큰 없어~ 돌아가 ~");
			filterChain.doFilter(request, response);
			return;
		}

		removePrefixToken = removePrefixToken(request);
		log.info(removePrefixToken(request));

		if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
			&& jwtProvider.isValidToken(removePrefixToken)) {

			Authentication authentication = jwtProvider.getAuthentication(removePrefixToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);

		}

		filterChain.doFilter(request, response);

	}

	private static String removePrefixToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token.startsWith("Bearer ") && StringUtils.hasText(token)) {
			//
			return token.substring(7);
		}
		return null;
	}
}

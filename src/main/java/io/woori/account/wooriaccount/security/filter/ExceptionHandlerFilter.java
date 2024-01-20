package io.woori.account.wooriaccount.security.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.woori.account.wooriaccount.security.utils.CookieUtil;
import io.woori.account.wooriaccount.security.utils.JwtProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.woori.account.wooriaccount.common.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.spi.service.contexts.SecurityContext;

/*
 * controller advice는 컨트롤러에서만 사용 가능하기 떄문에 에러 코드가 발생할 경우
 * 이를 처리하기 위해서 커스텀한 filter 입니다.
 * */
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {

	private final ObjectMapper mapper;
	private final JwtProvider jwtProvider;
	private final RedisTemplate<String, Object> redisTemplate;
	private final CookieUtil cookieUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			jwtProvider.isValidTokenWithException(JwtProvider.removePrefixToken(request));

			filterChain.doFilter(request, response);
		} catch (AuthenticationException authenticationException) {

			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());

			String email = request.getParameter("email");
			String pwd = request.getParameter("pwd");

			ResponseDTO<Map<String, String>> responseDTO = ResponseDTO.of(HttpStatus.UNAUTHORIZED, "로그인 정보가 올바르지 않습니다",
				Map.of(email, pwd));
			response.getWriter().write(mapper.writeValueAsString(responseDTO));

		} catch (ExpiredJwtException expiredJwtException){
			Optional<Cookie> op = cookieUtil.getCookie(request, "randomId");

			if (op.isPresent()){

				Cookie cookie = op.get();
				String randomId = cookie.getValue();
				String refreshToken = (String)redisTemplate.opsForHash().get(randomId, "refreshToken");


				// 1번 경우: 리프레시 토큰이 만료기간이 살아 있는 상황
				if (jwtProvider.isValidToken(JwtProvider.removePrefixToken(refreshToken))){
					Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
					List<String> roles= new ArrayList<>();
					for (GrantedAuthority authority : authorities) {
						roles.add(authentication.getName());
					}

					redisTemplate.opsForHash().delete(randomId, "accessToken");
					String accessToken = jwtProvider.createAccessToken(authentication.getName(), roles);
					redisTemplate.opsForHash().put(randomId, "accessToken", accessToken);
					redisTemplate.expire(randomId, Duration.ofDays(14));

					filterChain.doFilter(request,response);


				}
				// 2번 경우: 리프레시 토큰도 만료된 경우
				else if (!jwtProvider.isValidToken(JwtProvider.removePrefixToken(refreshToken))){
					redisTemplate.opsForHash().delete(randomId, "accessToken");
					redisTemplate.opsForHash().delete(randomId, "refreshToken");
					SecurityContextHolder.clearContext();
					cookieUtil.deleteCookie(request,response,"randomId");

					response.setStatus(HttpStatus.UNAUTHORIZED.value());
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
					ResponseDTO<Map<String, String>> responseDTO = ResponseDTO.of(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다. 다시 로그인 해주세요", null);
					response.getWriter().write(mapper.writeValueAsString(responseDTO));

				}
			}

		}

	}

}

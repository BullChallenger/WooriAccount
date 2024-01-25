package io.woori.account.wooriaccount.security.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.woori.account.wooriaccount.common.dto.ResponseDTO;
import io.woori.account.wooriaccount.security.model.PrincipalUser;
import io.woori.account.wooriaccount.security.utils.CookieUtil;
import io.woori.account.wooriaccount.security.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WooriOidcAuthSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtProvider jwtProvider;
	private final RedisTemplate<String, Object> redisTemplate;
	private final CookieUtil cookieUtil;
	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		PrincipalUser principalUser = (PrincipalUser)authentication.getPrincipal();

		log.info("WooriOidcAuthSuccessHandler : onAuthenticationSuccess");
		log.info("OAuth2 Authentication : {}", authentication);
		log.info("OAuth2 PrincipalUser : {}", principalUser);

		List<String> authorities = extractAuthority(authentication);
		
		String accessToken = jwtProvider.createAccessToken(principalUser.getEmail(), authorities);
		String refreshToken = jwtProvider.createRefreshToken(principalUser.getEmail(), authorities);

		log.info("accessToken : {}", accessToken);
		log.info("refreshToken : {}", refreshToken);

		response.setHeader("Authorization", "Bearer " + accessToken);

		String randomId = generateRandomId(10);
		log.info(randomId);
		redisTemplate.opsForHash().put(randomId, "accessToken", accessToken);
		redisTemplate.opsForHash().put(randomId, "refreshToken", refreshToken);
		redisTemplate.expire(randomId, Duration.ofDays(14)); //hash 사용에는 따로 따로 각자 만료시간 설정이 불가 ;

		cookieUtil.addCookie(response, "randomId", randomId);

		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
		String email = principalUser.getEmail();

		ResponseDTO<Map<String, String>> responseDTO = ResponseDTO.of(HttpStatus.OK, "회원 인증 성공",
			Map.of("email", email));
		response.getWriter().write(objectMapper.writeValueAsString(responseDTO));

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

	private String generateRandomId(int length) {
		StringBuilder sb = new StringBuilder(length);
		ThreadLocalRandom random = ThreadLocalRandom.current();

		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		for (int i = 0; i < length; i++) {
			int randomNum = random.nextInt(characters.length());
			sb.append(characters.charAt(randomNum));
		}
		return sb.toString();
	}

}

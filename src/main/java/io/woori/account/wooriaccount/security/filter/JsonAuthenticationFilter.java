package io.woori.account.wooriaccount.security.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.woori.account.wooriaccount.customer.domain.dto.LoginRequestDTO;
import lombok.extern.slf4j.Slf4j;


/*
 * 해당 필터는 AbstractAuthenticationProcessingFilter를 상속받아 구현하고 있습니다.
 * 기존에 사용한 필터인 UsernamepasswordAuthenticationFiler가 form이 없는 경우엔 적합하지 않기 때문입니다.
 *
 * 이때 successfulAuthentication()에서 동작하는 jwt 토큰 발급 작업은 successHandler에서 진행합니다.
 * successHandler를 생성자에 설정해주거나 security config 에서 설정해주어도 괜찮습니다.
 * */

@Slf4j
public class JsonAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private static final String DEFAULT_LOGIN_REQUEST_URI = "/customer/login";
	private static final String CONTENT_TYPE = "application/json";
	private final ObjectMapper mapper; //json으로 데이터를 넘겨 받기 때문에 매퍼 사용

	public JsonAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
		super(DEFAULT_LOGIN_REQUEST_URI);
		this.mapper = objectMapper;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException,
		IOException,
		ServletException {

		String contentType = request.getHeader("Content-Type");
		log.info("JsonAuthenticationFilter: attemptAuthentication의 content type ;{}", contentType);

		LoginRequestDTO requestDTO = mapper.readValue(request.getInputStream(), LoginRequestDTO.class);

		String email;
		String pwd;

		// request header 안에 content type이  json이 아니거나
		if (!contentType.equals(CONTENT_TYPE) || requestDTO.getEmail().isBlank() || requestDTO.getEmail().isEmpty() ||
			requestDTO.getPwd().isEmpty() || requestDTO.getPwd().isBlank()) {
			throw new BadCredentialsException("아이디값 비밀번호값 입력은 필수 ~");
		}

		email = requestDTO.getEmail();
		pwd = requestDTO.getPwd();

		log.info("email: {}", email);
		log.info("pwd: {}", pwd);

		UsernamePasswordAuthenticationToken unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(email,
			pwd);

		return getAuthenticationManager().authenticate(unauthenticated);
	}
}

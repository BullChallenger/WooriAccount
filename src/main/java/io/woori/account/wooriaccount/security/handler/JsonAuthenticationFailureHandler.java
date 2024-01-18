package io.woori.account.wooriaccount.security.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.woori.account.wooriaccount.common.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JsonAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private final ObjectMapper mapper;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {


		log.info("failureHandler");

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);



		ResponseDTO<Map<String, String>> responseDTO = ResponseDTO.of(HttpStatus.UNAUTHORIZED, exception.getMessage(),
			Map.of());
		response.getWriter().write(mapper.writeValueAsString(responseDTO));

	}
}

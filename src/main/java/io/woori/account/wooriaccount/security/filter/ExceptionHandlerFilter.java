package io.woori.account.wooriaccount.security.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.woori.account.wooriaccount.security.utils.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.woori.account.wooriaccount.common.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * controller advice는 컨트롤러에서만 사용 가능하기 떄문에 에러 코드가 발생할 경우
 * 이를 처리하기 위해서 커스텀한 filter 입니다.
 * */
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {

	private final ObjectMapper mapper;
	private final JwtProvider jwtProvider;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			if (jwtProvider.isValidToken(request.getHeader("Authorization"))){
				throw new AuthenticationServiceException("jwt 토큰이 만료 됐습니다.");
			}
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
		} catch (AuthenticationServiceException authenticationServiceException){
			// TODO 여기서 이제 JWT 관련 리프레시 토큰이 되면은 다시 액세스 토큰 발급해주고 안 되면은 다시 로그인 요청할 수 있게!

		}

	}

}

package io.woori.account.wooriaccount.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.woori.account.wooriaccount.customer.repository.CustomerCacheRepository;
import io.woori.account.wooriaccount.customer.repository.jpa.CustomerRepository;
import io.woori.account.wooriaccount.security.filter.ExceptionHandlerFilter;
import io.woori.account.wooriaccount.security.filter.JsonAuthenticationFilter;
import io.woori.account.wooriaccount.security.filter.JwtAuthenticationFilter;
import io.woori.account.wooriaccount.security.filter.JwtOncePerRequestFilter;
import io.woori.account.wooriaccount.security.handler.JsonAuthenticationFailureHandler;
import io.woori.account.wooriaccount.security.handler.JsonAuthenticationSuccessHandler;
import io.woori.account.wooriaccount.security.provider.JwtAuthenticationProvider;
import io.woori.account.wooriaccount.security.service.CustomUserDetailsService;
import io.woori.account.wooriaccount.security.utils.CookieUtil;
import io.woori.account.wooriaccount.security.utils.JwtProvider;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final CustomerRepository customerRepository;
	private final CustomerCacheRepository customerCacheRepository;
	private final JwtProvider jwtProvider;
	private final RedisTemplate<String, Object> redisTemplate;
	private final CookieUtil cookieUtil;
	private final ObjectMapper objectMapper;
	private final CorsConfig config;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// token 사용을 위해 csrf를 diable 합니다.
		http.csrf().disable();
		http.cors().disable();

		//세션을 대신해 jwt를 사용해 로그인 유지를 진행할 예정으로 해당 세션 사용 옵션을 stateless하게 설정합니다.
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests()
			.antMatchers("/swagger-ui/", "/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**")
			.permitAll();

		//우선은 모든 url 접근에 허용 합니다. -> 추후 변경 ok
		http.authorizeRequests((auth) ->
			auth.regexMatchers("/customer/login", "/api/customers/signUp", "/", "/swagger-ui/*").permitAll()
				.anyRequest().authenticated());

		http.formLogin().disable();

		http.addFilter(config.corsFilter())
			.addFilterAt(jsonAuthenticationFiler(), UsernamePasswordAuthenticationFilter.class)
			.addFilterAfter(JwtOncePerRequestFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterAfter(exceptionHandlerFilter(), UsernamePasswordAuthenticationFilter.class)
			.authenticationProvider(
				jwtAuthenticationProvider()); // UsernamePasswordAuthenticatioFilter가 작동할 때 jwt custom filter를 사용하려 합니다.

		return http.build();
	}

	@Bean
	public AbstractAuthenticationProcessingFilter jsonAuthenticationFiler() throws Exception {
		AbstractAuthenticationProcessingFilter jsonAuthenticationFilter = new JsonAuthenticationFilter(
			authenticationManager(null), objectMapper);
		jsonAuthenticationFilter.setAuthenticationManager(new ProviderManager(
			jwtAuthenticationProvider()));//실제로 authentication manager는 이 필터에 넘겨줘야 하는데 이 작업은 직접 구현한 manager를 넣어도 되지만 해당 프로젝트에선 구현하고 있지 않아서 이방식을 사용
		jsonAuthenticationFilter.setAuthenticationSuccessHandler(jsonAuthenticationSuccessHandler());
		jsonAuthenticationFilter.setAuthenticationFailureHandler(jsonAuthenticationFailureHandler());
		return jsonAuthenticationFilter;
	}

	@Bean
	public AuthenticationSuccessHandler jsonAuthenticationSuccessHandler() {
		return new JsonAuthenticationSuccessHandler(jwtProvider, redisTemplate, cookieUtil, objectMapper);

	}

	@Bean
	public AuthenticationProvider jwtAuthenticationProvider() {
		return new JwtAuthenticationProvider(new CustomUserDetailsService(customerRepository, customerCacheRepository),
			(BCryptPasswordEncoder)passwordEncoder());
	}

	//@Bean
	public UsernamePasswordAuthenticationFilter jwtAuthenticationFilter() throws Exception {

		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(null),
			jwtProvider, redisTemplate, cookieUtil);
		jwtAuthenticationFilter.setAuthenticationManager(new ProviderManager(jwtAuthenticationProvider()));
		//jwtAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());

		return jwtAuthenticationFilter;

	}

	@Bean
	public OncePerRequestFilter exceptionHandlerFilter() {
		return new ExceptionHandlerFilter(objectMapper, jwtProvider, redisTemplate, cookieUtil);

	}

	@Bean
	public AuthenticationFailureHandler jsonAuthenticationFailureHandler() {
		return new JsonAuthenticationFailureHandler(objectMapper);
	}

	@Bean
	public OncePerRequestFilter JwtOncePerRequestFilter() {
		return new JwtOncePerRequestFilter(jwtProvider);

	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws
		Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	/*
	 * 비밀번호를 평문 저장하지 않기 위해서 사용하는 Encoder를 빈등록 했습니다.
	 * */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
			.antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/h2-console/**");
	}

}

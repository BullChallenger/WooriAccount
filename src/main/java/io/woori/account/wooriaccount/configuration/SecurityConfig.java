package io.woori.account.wooriaccount.configuration;


import io.woori.account.wooriaccount.repository.jpa.CustomerRepository;
import io.woori.account.wooriaccount.security.filter.JwtAuthenticationFilter;
import io.woori.account.wooriaccount.security.filter.JwtOncePerRequestFilter;
import io.woori.account.wooriaccount.security.provider.JwtAuthenticationProvider;
import io.woori.account.wooriaccount.security.service.CustomUserDetailsService;
import io.woori.account.wooriaccount.security.utils.CookieUtil;
import io.woori.account.wooriaccount.security.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomerRepository customerRepository;
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, Object> redisTemplate;
    private final CookieUtil cookieUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // token 사용을 위해 csrf를 diable 합니다.
        http.csrf().disable();
        http.cors().disable();

        //세션을 대신해 jwt를 사용해 로그인 유지를 진행할 예정으로 해당 세션 사용 옵션을 stateless하게 설정합니다.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //우선은 모든 url 접근에 허용 합니다. -> 추후 변경 ok
        http.authorizeRequests().anyRequest().permitAll();

        // form login 시 작동하는 필터를 등록해줍니다. (다른 로그인 방식이라면 usernamepasswordAuthentication 말고 다른 필터 사용 ok)
        http.formLogin()
                .loginPage("/customer/login").disable();
        http.addFilterAt(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(JwtOncePerRequestFilter(), UsernamePasswordAuthenticationFilter.class)
            .authenticationProvider(jwtAuthenticationProvider());// UsernamePasswordAuthenticatioFilter가 작동할 때 jwt custom filter를 사용하려 합니다.


        return http.build();
    }


    @Bean
    public AuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(new CustomUserDetailsService(customerRepository), (BCryptPasswordEncoder) passwordEncoder());
    }

    @Bean
    public UsernamePasswordAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(null), jwtProvider, redisTemplate,cookieUtil);
        jwtAuthenticationFilter.setAuthenticationManager(new ProviderManager(jwtAuthenticationProvider()));

        return jwtAuthenticationFilter;

    }

    @Bean
    public OncePerRequestFilter JwtOncePerRequestFilter(){
        return new JwtOncePerRequestFilter(jwtProvider, cookieUtil, redisTemplate);

    }
    /*
    * AbstractAuthenticationProcessingFilter 는 객체 생성시 AuthenticationManager 를 필수로 요구합니다.
    * AuthenticationManager 는 스프링 시큐리티가 초기화 되면서 생성하고 있는데 AuthenticationManager 를 바로 참조할 수 있는 API 가 제공되지 않습니다.
    * 대신에 초기화 때 AuthenticationManager 를 생성한 설정 클래스를 참조할 수 있습니다.
    * AuthenticationConfiguration 가 초기화 때 AuthenticationManager 를 생성한 설정 클래스인데 이 클래스로부터 AuthenticationManager 를 얻을 수 있습니다.
    *
    * 해당 인터페이스를 직접 구현하지 않는 이유는 스프링 시큐리티에서 제공하는 설정을 활요하는 것이 더 이점이 많기 때문입니다.
    * 그래서 해당 작업은 이렇게 매니저를 불러와서 사용하는 방식으로 코드를 작성했습니다.
    * */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*
    * 비밀번호를 평문 저장하지 않기 위해서 사용하는 Encoder를 빈등록 했습니다.
    * */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

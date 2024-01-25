package io.woori.account.wooriaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class WooriAccountApplication {
	public static void main(String[] args) {
		SpringApplication.run(WooriAccountApplication.class, args);
	}

	/*
	 * 비밀번호를 평문 저장하지 않기 위해서 사용하는 Encoder를 빈등록 했습니다.
	 * */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

package io.woori.account.wooriaccount.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.woori.account.wooriaccount.encryption.BCryptImpl;
import io.woori.account.wooriaccount.encryption.EncryptHelper;

@Configuration
public class EncryptConfig {
	@Bean
	public EncryptHelper encryptConfigure() {
		return new BCryptImpl();
	}

}

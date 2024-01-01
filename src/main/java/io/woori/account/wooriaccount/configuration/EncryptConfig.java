package io.woori.account.wooriaccount.configuration;

import io.woori.account.wooriaccount.encryption.BCryptImpl;
import io.woori.account.wooriaccount.encryption.EncryptHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptConfig {
    @Bean
    public EncryptHelper encryptConfigure() {
        return new BCryptImpl();
    }
}

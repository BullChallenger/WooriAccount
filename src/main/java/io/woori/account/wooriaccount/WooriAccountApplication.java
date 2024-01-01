package io.woori.account.wooriaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WooriAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(WooriAccountApplication.class, args);
    }

}

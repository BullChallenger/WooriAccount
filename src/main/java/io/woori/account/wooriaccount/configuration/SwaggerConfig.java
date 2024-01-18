package io.woori.account.wooriaccount.configuration;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.OAS_30)
			.select()
			.apis(RequestHandlerSelectors.basePackage("io.woori.account.wooriaccount"))
			.paths(PathSelectors.ant("/api/**"))
			.build()
			.apiInfo(apiInfo())
			.securitySchemes(List.of(apiKeyHeader()))
			.securityContexts(Collections.singletonList(securityContext()));
	}

	@Bean
	public InternalResourceViewResolver defaultViewResolver() {
		return new InternalResourceViewResolver();
	}

	private ApiKey apiKeyHeader() {
		return new ApiKey("Authorization", "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
			.securityReferences(List.of(headerAuthReference()))
			.build();
	}

	private SecurityReference headerAuthReference() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[] {authorizationScope};
		return new SecurityReference("Authorization", authorizationScopes);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("Swagger 문서 제목")
			.description("Swagger 문서 설명")
			.version("1.0")
			.build();
	}

}

package com.kognitiv.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableOpenApi
public class SwaggerConfiguration {

	private static String title;
	private static String description;
	private static String version;

	private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<String>(
			Collections.singleton("application/json"));

	@Value("${swagger.service}")
	public void setService(String serviceName) {
		SwaggerConfiguration.title = serviceName;
	}

	@Value("${swagger.version}")
	public void setVersion(String version) {
		SwaggerConfiguration.version = version;
	}

	@Value("${swagger.description}")
	public void setDescription(String description) {
		SwaggerConfiguration.description = description;
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.OAS_30)
				.apiInfo(new ApiInfo(SwaggerConfiguration.title, SwaggerConfiguration.description,
						SwaggerConfiguration.version, null, null, null, null))
				.useDefaultResponseMessages(false).select()
				.apis(RequestHandlerSelectors.basePackage("com.kognitiv")).build()

				.produces(DEFAULT_PRODUCES_AND_CONSUMES).consumes(DEFAULT_PRODUCES_AND_CONSUMES);
	}

}

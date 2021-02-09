package com.phoenix.md5reader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAsync
@EnableSwagger2
public class PhoenixMd5ReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhoenixMd5ReaderApplication.class, args);
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.phoenix.md5reader.controller"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());

	}

	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
		return new ApiInfo("MD5 REST API", "MD5 Rest API Document for App", "API TOS", "Terms of service",
				"Agile Soft Systems India Pvt Ltd", "License of API", "");
	}
}

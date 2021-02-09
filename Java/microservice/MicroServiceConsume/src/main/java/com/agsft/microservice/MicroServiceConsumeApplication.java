package com.agsft.microservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.java.Log;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@Log
public class MicroServiceConsumeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceConsumeApplication.class, args);
	}
	
	 @PostConstruct
	    public void init(){
	        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));   // It will set UTC timezone
	        log.info("Spring boot application running in UTC timezone :"+new Date());   // It will print UTC timezone
	    }
	
	@Bean
	public Docket swaggerSettings() {
		Parameter parameter = new ParameterBuilder().name("Authorization").description("Authorization Token")
				.modelRef(new ModelRef("string")).parameterType("header").required(false).build();
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(parameter);
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.agsft.microservice.controller")).paths(PathSelectors.any()).build()
				.apiInfo(apiInfo()).pathMapping("/").globalOperationParameters(parameters)
				.useDefaultResponseMessages(false);

	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("Microservice Consume Api", "Agsft service Rest API Document", "API TOS",
				"Terms of service", "Agile Soft Systems India Pvt Ltd", "License of API", "");
		return apiInfo;
	}
}

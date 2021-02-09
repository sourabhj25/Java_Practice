package com.agsft.authservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.ui.velocity.VelocityEngineFactory;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

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
@EnableJpaAuditing
@EnableSwagger2
@Log
public class AuthenticationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}
	
	@Bean
	public Validator jsr303Validator() {
		return new LocalValidatorFactoryBean();
	}
	
	@Bean
    public VelocityEngine getVelocityEngine() throws VelocityException, IOException{
        VelocityEngineFactory factory = new VelocityEngineFactory();
        Properties props = new Properties();
        props.put("resource.loader", "classpath");
        props.put("classpath.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        factory.setVelocityProperties(props);   
        VelocityEngine ve = factory.createVelocityEngine();
        ve.init();
        return ve;      
    }
	
	@Bean
	public Docket swaggerSettings() {
		Parameter parameter = new ParameterBuilder().name("Authorization").description("Authorization Token")
				.modelRef(new ModelRef("string")).parameterType("header").required(false).build();
		Parameter accesKey = new ParameterBuilder().name("accessKey").description("Client Access Key")
				.modelRef(new ModelRef("string")).parameterType("header").required(false).build();
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(accesKey);
		parameters.add(parameter);
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.agsft.authservice.controller")).paths(PathSelectors.any()).build()
				.apiInfo(apiInfo()).pathMapping("/").globalOperationParameters(parameters)
				.useDefaultResponseMessages(false);

	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("Authentication Microservice ", "Agsft service Rest API Document", "API TOS",
				"Terms of service", "Agile Soft Systems India Pvt Ltd", "License of API", "");
		return apiInfo;
	}
	
}

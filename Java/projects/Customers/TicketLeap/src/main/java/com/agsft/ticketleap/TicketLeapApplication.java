package com.agsft.ticketleap;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.agsft.ticketleap.config.MailPropertiesConfig;
import com.agsft.ticketleap.constants.MailNameConstants;

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
@EnableAsync
@EnableSwagger2
@EnableAutoConfiguration
public class TicketLeapApplication {

	@Autowired
	MailPropertiesConfig props;

	public static void main(String[] args) {
		SpringApplication.run(TicketLeapApplication.class, args);
	}

	@Bean
	JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(props.getHost());
		mailSender.setPort(props.getPort());
		mailSender.setUsername(props.getUsername());
		mailSender.setPassword(props.getPassword());
		/*
		 * mailSender.setHost("smtp.gmail.com"); mailSender.setPort(465);
		 * mailSender.setUsername("agilesoft2015@gmail.com");
		 * mailSender.setPassword("welcome123$");
		 */
		Properties mailProperties = new Properties();
		mailProperties.put(MailNameConstants.MAIL_AUTH, true);
		mailProperties.put(MailNameConstants.MAIL_SOCKET, MailNameConstants.MAIL_SOCKET_VALUE);
		mailProperties.put(MailNameConstants.MAIL_TTS, true);
		mailSender.setJavaMailProperties(mailProperties);
		mailSender.setProtocol(MailNameConstants.MAIL_PROTOCOL);

		return mailSender;

	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public Docket productApi() {
		Parameter parameter = new ParameterBuilder().name("Authorization").description("Authorization Token")
				.modelRef(new ModelRef("string")).parameterType("header").required(false).build();
		List<Parameter> parameters = new ArrayList<>();
		parameters.add(parameter);

		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.agsft.ticketleap.controller")).paths(PathSelectors.any())
				.build().apiInfo(apiInfo()).pathMapping("/").globalOperationParameters(parameters)
				.useDefaultResponseMessages(false);

	}

	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
		return new ApiInfo("TicketLeap REST API", "TicketLeap Rest API Document for App", "API TOS", "Terms of service",
				"Agile Soft Systems India Pvt Ltd", "License of API", "");
	}

	@Bean
	public Validator jsr303Validator() {
		return new LocalValidatorFactoryBean();
	}
}

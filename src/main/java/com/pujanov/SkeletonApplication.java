package com.pujanov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * 
 * @author Pujan KC <pujanov69@gmail.com>
 * 
 *
 */
@SpringBootApplication(exclude = {
		SecurityAutoConfiguration.class
})
@ComponentScan("com.pujanov")
@EnableSwagger2
public class SkeletonApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkeletonApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}
	
	@Bean
	ObjectMapper objectMapperBean() {
		return new ObjectMapper();
	}
	
	 @Bean
	 	   public Docket productApi() {
	 	      return new Docket(DocumentationType.SWAGGER_2).select()
	 	         .apis(RequestHandlerSelectors.basePackage("com.pujanov.resources")).build();
	 	   }
}

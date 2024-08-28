package com.yaacreations.swagger.apps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication(scanBasePackages = {"com.*"})
@EntityScan(basePackages = {"com.*"})
@EnableJpaRepositories (basePackages = {"com.*"})
@OpenAPIDefinition
public class SwaggerProjectWithSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwaggerProjectWithSecurityApplication.class, args);
	}
	
//	@Bean
//	public OpenAPI customOpenAPI() {
//		
//		return new OpenAPI()
//				   .info(new Info()
//						 .title("Swagger Sample")
//						 .description("Swagger Project with Security")
//						 .version("1.0.0"));
//		
//	}

}

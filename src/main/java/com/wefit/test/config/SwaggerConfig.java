package com.wefit.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;

@OpenAPIDefinition(info =
@io.swagger.v3.oas.annotations.info.Info(title = "Voting session ",
        version = "V1",
        description = "API documentation, test for the company Webfit",
        contact = @Contact(
                url = "https://www.youtube.com/@javanaveia",
                name = "Fernando Silva", email = "nando.systempro@hotmail.com")
))


@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		  return new OpenAPI()
	        		
	        	
	                .info(
	                        new io.swagger.v3.oas.models.info.Info()
	                                .title("API Webfit")
	                                .version("v1")
	                                .license(new License()
	                                        .name("Apache  2.0")
	                                        .url("http://springdoc.org")
	                                )
	                );
	}

}

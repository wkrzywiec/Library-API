package com.wkrzywiec.spring.libraryrest.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {  
	
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())
          .build()
          .apiInfo(apiInfo());                                           
    }
    
    private ApiInfo apiInfo() {
    	return new ApiInfo(
    			"Library Portal API",
    			"This a simple REST service and is part of the other project - Library Portal. It allows to perform CRUD actions on basic resources, like users or books entities, and retrieve them as a JSON respond.",
    			"1.0", "urn:tos",
    			new Contact("Wojciech Krzywiec", "https://github.com/wkrzywiec", ""),
    			"Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0:",  Collections.emptyList());
    }
}

package com.example.clienttest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
            .apis(RequestHandlerSelectors.basePackage("com.example.clienttest.web"))
            .paths(PathSelectors.ant("/oauth2example/resource/**"))
            .build()
            .apiInfo(appInfo());
    }

    private ApiInfo appInfo() {
        return new ApiInfoBuilder()
            .title("oauth2-test")
            .description("example client for a protected test resource server")
            .version("1.0")
            .contact(createContact())
            .build();
    }

    private Contact createContact() {
        return new Contact("Istvan Bartuszek", null, "istvan.bartuszek@gmail.com");
    }

}

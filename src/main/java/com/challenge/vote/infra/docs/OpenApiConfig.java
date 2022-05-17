package com.challenge.vote.infra.docs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static java.lang.Boolean.FALSE;
import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@EnableSwagger2
@Configuration
public class OpenApiConfig {

    @Value("${project.description}")
    private String description;

    @Value("${project.name}")
    private String name;

    @Value("${project.version}")
    private String version;

    @Bean
    public Docket api() {
        return new Docket(SWAGGER_2)
                .useDefaultResponseMessages(FALSE)
                .groupName("V1")
                .select()
                .apis(basePackage("com.challenge.vote.infra.controllers"))
                .paths(any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(name)
                .description(description)
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .termsOfServiceUrl("/service.html")
                .version(version)
                .contact(new Contact("Anderson Costa da Mata", "https://github.com/acdmAnderson", "acdm.anderson@hotmail.com"))
                .build();
    }
}

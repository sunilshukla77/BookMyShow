package com.book.my.show.config;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.book.my.show"))
                .paths(PathSelectors.regex("/bookmyshow.*"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(metadata());
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder().title("BOOK MY SHOW SWAGGER INTERFACE")
                .description("REST APIs for Book My Show Portal")
                .version("1.0")
                .contact(new Contact("Sunil Shukla", "https://github.com/sunilshukla77", "sunil.jobs77@gmail.com"))
                .build();
    }
}

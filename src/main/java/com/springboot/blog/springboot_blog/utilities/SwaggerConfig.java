package com.springboot.blog.springboot_blog.utilities;

import org.springframework.http.HttpHeaders;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
@SecurityScheme(
    name = "securityToken",
    type = SecuritySchemeType.HTTP,
    paramName = HttpHeaders.AUTHORIZATION,
    in = SecuritySchemeIn.HEADER,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
        .info(new Info()
        .title("SpringBlog API")
        .description("Documentación de endpoints para gestión de aplicación de publicaciónes")
        .version("1.0.0")
        .contact(new Contact()
        .name(": joako.alvarez.07@gmail.com")
        .email("joako.alvarez.07@gmail.com")))
        .addSecurityItem(new SecurityRequirement().addList("securityToken"));
    }
}

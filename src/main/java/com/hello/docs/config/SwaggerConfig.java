package com.hello.docs.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // OpenAPI 객체를 등록하고, application.properties 에서도 추가 설정을 약간 해줍니다.
    // 여기까지의 최소한의 설정부터 시작해서 커스텀해나가면 됩니다.
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Swagger Docs"))
                .addServersItem(new Server()
                        .url("/"))
                .components(new Components()
                        .addSecuritySchemes( // SecurityScheme 을 추가해서 인증을 요구하는 API 를 위해 인증방식에 대해 정의할 수 있습니다.
                                "JWT Bearer token",
                                new SecurityScheme()
                                        .name("JWT Bearer token")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        ))
                .addSecurityItem(new SecurityRequirement()
                        .addList("JWT Bearer token"));
    }
}

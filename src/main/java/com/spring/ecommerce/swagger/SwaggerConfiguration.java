package com.spring.ecommerce.swagger;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {  // 메서드 이름을 유니크하게 지정
        return new OpenAPI()
                .info(new Info()
                        .title("Ecommerce API")  // API 제목
                        .version("v1.0")         // API 버전
                        .description("주문/결제/상품/잔액 API") // 설명
                );
    }

}

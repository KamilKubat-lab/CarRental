package com.car.rental.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI carRentalOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Car Rental API")
                        .description("API for reserving cars (Sedan, SUV, Van)")
                        .version("1.0.0"));
    }
}

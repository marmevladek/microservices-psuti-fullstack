package ru.psuti.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.psuti.apigateway.security.JwtAuthFilter;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtAuthFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("USER-SERVICE", r -> r.path("/student/**").filters(f -> f.filter(filter)).uri("lb://USER-SERVICE"))
                .route("USER-SERVICE", r -> r.path("/teachers/**").filters(f -> f.filter(filter)).uri("lb://USER-SERVICE"))
                .route("AUTH-SERVICE", r -> r.path("/auth/**").filters(f -> f.filter(filter)).uri("lb://AUTH-SERVICE"))
                .route("FILE-SERVICE", r -> r.path("/files/**").filters(f -> f.filter(filter)).uri("lb://FILE-SERVICE"))
                .build();
    }
}

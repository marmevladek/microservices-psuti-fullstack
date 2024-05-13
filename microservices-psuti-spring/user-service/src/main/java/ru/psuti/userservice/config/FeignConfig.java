package ru.psuti.userservice.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.psuti.userservice.external.decoder.CustomErrorDecoder;

@Configuration
public class FeignConfig {

    @Bean
    ErrorDecoder feignErrorDecoder() {
        return new CustomErrorDecoder();
    }
}

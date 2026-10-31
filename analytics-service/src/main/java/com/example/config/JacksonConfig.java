package com.example.config;
import org.springframework.amqp.support.converter.*;
import org.springframework.context.annotation.*;

@Configuration
public class JacksonConfig {

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

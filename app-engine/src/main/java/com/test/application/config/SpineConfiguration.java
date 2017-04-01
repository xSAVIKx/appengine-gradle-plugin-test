package com.test.application.config;

import com.test.application.domain.user.UserBoundedContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

@Configuration
public class SpineConfiguration {
    @Bean
    public UserBoundedContext userBoundedContext() {
        final UserBoundedContext userBoundedContext = UserBoundedContext.getInstance();
        return userBoundedContext;
    }

    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }
}

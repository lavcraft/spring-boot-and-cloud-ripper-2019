package com.naya.exams.frontendstarter.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResultWrapperConfiguration {
    @Bean
    public ResultWrapperAdvice resultWrapperAdvice() {
        return new ResultWrapperAdvice();
    }
}

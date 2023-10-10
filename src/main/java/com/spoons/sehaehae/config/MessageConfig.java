package com.spoons.sehaehae.config;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageConfig {
    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource message = new ReloadableResourceBundleMessageSource();
        message.setBasename("classpath:/messages/messages");
        message.setDefaultEncoding("UTF-8");
        return message;
    }

    @Bean
    public MessageSourceAccessor accessor(){
        return new MessageSourceAccessor(messageSource());

    }


}

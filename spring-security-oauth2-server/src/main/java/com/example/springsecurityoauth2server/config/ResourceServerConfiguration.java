package com.example.springsecurityoauth2server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
/*
    @EnableResourceServer is a convenient annotation for OAuth2 Resource Servers, it enables a Spring Security filter that authenticates requests via an
    incoming OAuth2 token
 */
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.anonymous().disable()
                .requestMatchers() //versus .authorizeRequests()
                    .mvcMatchers("/api/**")
                .and()
                    .authorizeRequests()
                        .mvcMatchers("/api/**").access("hasRole('ADMIN') or hasRole('USER')")
                .and()
                    .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }

}
package com.example.springsecurityoauth2server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public final static String WEB_REALM = "WEB_REALM";

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{bcrypt}$2a$10$LjZzLZDBQbeNRSTnav2cQOHPM0k.Oe2Km4EPJ2lkn5VKD21S4uvVW").roles("USER") // 'pwd' encoded with bcrypt
                .and()
                .withUser("adminuser").password("{bcrypt}$2a$10$LjZzLZDBQbeNRSTnav2cQOHPM0k.Oe2Km4EPJ2lkn5VKD21S4uvVW").roles("ADMIN", "USER"); // 'pwd' encoded with bcrypt
    }

    /*
        With above method we build an AuthenticationManager but that's not defined as bean by default? So if you want to use it (in the AuthServerConfiguration)
        you have to declare the method as a bean
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    @Order(Ordered.HIGHEST_PRECEDENCE)
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Spring Security will never create an HttpSession and it will never use it to obtain the SecurityContext
                .and()
                    .csrf().disable()
                    .authorizeRequests()
                        .mvcMatchers("/about").permitAll()
                        .mvcMatchers("/signup").permitAll()
                        .mvcMatchers("/oauth/token").permitAll()
                .mvcMatchers("/api/**").permitAll()
                        .anyRequest().authenticated()
                .and()
                    .httpBasic().realmName(WEB_REALM);
    }

    // Use this to generate new passwords
    public static void main(String[] args) {
        String password = "pwd";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        System.out.println(hashedPassword);
    }
}
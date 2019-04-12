package com.example.springsecurityoauth2server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/*
	https://projects.spring.io/spring-security-oauth/docs/oauth2.html
	http://sivatechlab.com/secure-rest-api-using-spring-security-oauth2/

 */
@SpringBootApplication
public class SpringSecurityOauth2ServerApplication {


	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(SpringSecurityOauth2ServerApplication.class, args);
		//OAuth2AuthenticationProcessingFilter bean = ctx.getBean(OAuth2AuthenticationProcessingFilter.class);
	}

}

/*
	FAQ
	problem:
		java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
	solution:
		https://www.mkyong.com/spring-boot/spring-security-there-is-no-passwordencoder-mapped-for-the-id-null/
		Since Spring Security 5, the default PasswordEncoder has changed from NoOpPasswordEncoder to DelegatingPasswordEncoder (requiring Password Storage Format)

		prefix the pwd/secret with {noop}, both on the
			SecurityConfiguration.configureGlobal(AuthenticationManagerBuilder auth) &
			AuthServerConfiguration.configure(ClientDetailsServiceConfigurer clients)
		ex.
			.withUser("crmadmin").password("{noop}crmpass")
			.secret("{noop}web-client-secret")

		EXTRA INFO
			PasswordEncoder (interface & implementations
				NoOpPasswordEncoder
				BCryptPasswordEncoder
				DelegatingPasswordEncoder
				...

			By defining a BCryptPasswordEncoder bean you don't have to specify the {bcrypt} prefix.
			It's not yet clear, but it looks (TODO) like Spring security uses some chain of going through the PasswordEncoders defined to encode the pwd.
			First match found wins?

			Both the AuthServer & SecurityConfigurations in-memory-user-stores both pick up the encoder, even if you don't specify them (spring boot magic?)

			https://stackoverflow.com/questions/54115670/will-bcryptpasswordencoder-automatically-encode-the-default-password-created-by


	problem:
		When requesting a token /oauth/token
		got the error: "Unsupported grant type: password"
	solution:
		https://stackoverflow.com/questions/28254519/spring-oauth2-authorization-server
		To use password grant you need to provide an authentication manager to the authorization server (in the empty method with the, so it can authenticate users

		endpoints.tokenStore(tokenStore())
				 .authenticationManager(this.authenticationManager)


*/
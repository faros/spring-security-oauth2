package com.example.springsecurityoauth2server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@EnableAuthorizationServer
@Configuration
public class AuthServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private static final int ONE_DAY = 60 * 60 * 24;
    private static final int SEVEN_DAYS = 60 * 60 * 24 * 7;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /*

     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("web-client")
                .secret("{bcrypt}$2a$10$2QggqYJ0csi.6YuzS8nWKuzwQOsUNx2RymQ80XI4Mq60LsK4GHWdS") // 'web-client-secret' encoded with bcrypt
                .scopes("read", "write") //The scope to which the client is limited
                .authorizedGrantTypes("password", "refresh_token") // Grant types that are authorized for the client to use
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT") //Authorities that are granted to the client (regular Spring Security authorities)
                .accessTokenValiditySeconds(60)
                .refreshTokenValiditySeconds(SEVEN_DAYS);
    }

    /*

     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
        //.userApprovalHandler(null)
        .authenticationManager(this.authenticationManager);
    }

    /*

     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.realm(SecurityConfiguration.WEB_REALM);
        /*
            A realm is a security policy domain defined for a web or application server. A realm contains a collection of users, who may or may not be assigned to a group.

            https://www.baeldung.com/spring-security-multiple-entry-points
            When using Java configuration, the way to define multiple security realms is to have multiple @Configuration classes that extend the WebSecurityConfigurerAdapter
         */
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
        handler.setTokenStore(tokenStore);
        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        handler.setClientDetailsService(clientDetailsService);
        return handler;
    }

    @Bean
    public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }
}
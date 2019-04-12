# Spring Security OAuth2

Demo project demonstrating the use of OAuth2 for securing REST endpoints accessed by an Angular 7 application.

## Dependencies

**Spring Boot starters**

* spring-cloud-starter-oauth2
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-oauth2</artifactId>
</dependency>
```
* spring-boot-starter-oauth2-resource-server
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
```
* spring-boot-starter-oauth2-client
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-client</artifactId>
</dependency>
```
**Regular dependencies (no boot-starters)**

To use the auto-configuration features in this library, you need **spring-security-oauth2**, which has the OAuth 2.0 primitives and **spring-security-oauth2-autoconfigure**.
Note that you need to specify the version for spring-security-oauth2-autoconfigure, since it is not managed by Spring Boot any longer, though it should match Boot’s version anyway.

For JWT support, you also need **spring-security-jwt** (is a dependency of spring-security-oauth2-autoconfigure)

* spring security OAuth

https://spring.io/projects/spring-security-oauth

```xml
<dependency>
    <groupId>org.springframework.security.oauth</groupId>
    <artifactId>spring-security-oauth2</artifactId>
    <version>2.3.4.RELEASE</version>
</dependency>
```
* spring-security-oauth2-autoconfigure
```xml
<dependency>
    <groupId>org.springframework.security.oauth.boot</groupId>
    <artifactId>spring-security-oauth2-autoconfigure</artifactId>
    <version>2.1.4.RELEASE</version>
</dependency>
  ```

# TODO
https://spring.io/guides/tutorials/spring-boot-oauth2/

Because one of the samples is a full OAuth2 Authorization Server we have used the shim JAR which supports bridging from Spring Boot 2.0 to the old Spring Security OAuth2 library. The simpler samples could also be implemented using the native OAuth2 support in Spring Boot security features. The configuration is very similar.

https://docs.spring.io/spring-security-oauth2-boot/docs/current-SNAPSHOT/reference/htmlsingle/

The following projects are in maintenance mode:

* spring-security-oauth2
* spring-security-oauth2-autoconfigure

You are, of course, welcome to use them, and we will help you out!

However, before selecting **spring-security-oauth2** and **spring-security-oauth2-autoconfigure**, you should check out Spring Security’s [feature matrix](https://github.com/spring-projects/spring-security/wiki/OAuth-2.0-Features-Matrix) to see if the new first-class support meets your needs.

https://github.com/spring-projects/spring-security/wiki/OAuth-2.0-Features-Matrix

We will provide bug/security fixes and consider adding minor features but we will not be adding major features. Our plan going forward is to build all the features currently in **Spring Security OAuth** into **Spring Security 5.x**. After Spring Security has reached feature parity with Spring Security OAuth, we will continue to support bugs and security fixes for at least one year.


The **Spring Security OAuth Boot 2 Autoconfig** project is a port of the Spring Security OAuth auto-configuration contained in Spring Boot 1.5.x. If you would like to use Spring Security OAuth in Spring Boot 2.0, you must explicitly include the following dependency in your project:

groupId: org.springframework.security.oauth.boot

artifactId: spring-security-oauth2-autoconfigure

## Authorization server


## Resource server
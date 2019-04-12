package com.example.springsecurityoauth2server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {

    // If you get an error creating this bean, please remember to activate the "dev" profile in spring
    private final Set<String> allowedOrigins;
    
    @Autowired
    public CORSFilter(@Value("${oauth.server.origin}") String origin) {
		this.allowedOrigins = new HashSet<>(Arrays.asList (origin.split(",")));
	}

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        
	    response.setHeader("Access-Control-Allow-Origin",checkOriginHeader(request));
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
	    response.setHeader("Access-Control-Max-Age", "3600");
	    response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, x-auth-token, origin, content-type, accept");
	    
	    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
	        response.setStatus(HttpServletResponse.SC_OK);
	    } else {
	        chain.doFilter(req, res);
	    }
        /*else {
        	log.warn("An attempt has been made to contact server with unknown origin: " + originHeader);
        	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }*/
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
    
    public String checkOriginHeader(HttpServletRequest request) {
    	String originHeader = request.getHeader("Origin");
    	 if(allowedOrigins.contains(originHeader)){
    		 return originHeader;
    	 }
    	 return "";
    }
}
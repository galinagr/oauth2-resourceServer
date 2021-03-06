package com.example.ResourceServer.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyCloakRoleConverter());
        http.authorizeRequests((requests) -> {
                    try {
                        requests
                                .antMatchers(HttpMethod.GET, "/users/status/check")
//                                .hasAuthority("SCOPE_profile")
                                .hasRole("developer")
                                .anyRequest().authenticated()
                                .and()
                                .oauth2ResourceServer()
                                .jwt()
                        .jwtAuthenticationConverter(jwtAuthenticationConverter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}

package com.hitzseb.currencyTrader.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.csrf().disable().authorizeHttpRequests()
                .requestMatchers("/", "/register", "/login", "/images/**",
                        "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**",
                        "/api/v1/exchange*", "/api/v1/variation*").permitAll()
                .requestMatchers("/*/save", "/*/*/edit", "/*/*/delete").hasAuthority("ADMIN")
                .anyRequest().authenticated()
//                .and().headers().frameOptions().sameOrigin()
                .and().formLogin().loginPage("/login")
                .and().build();
    }
}

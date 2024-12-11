package com.springboot.blog.springboot_blog.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.blog.springboot_blog.auth.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    //Creo la cadena de filtros
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authRequest -> authRequest
        .requestMatchers("/auth/**").permitAll()
        .requestMatchers("/api/**").permitAll()
        .anyRequest().authenticated())
        //Desactivo los estados de sesion de sprin security para usar jwt
        .sessionManagement(sessionManager -> 
            sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        //Pongo el jwtAuthFilter antes del UsernamePasswordAuthenticationFilter el cual es el filtro predeterminado de Spring Security. 
        //Así consigo que se ejecute primero el filtro personalizado. 
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }
}

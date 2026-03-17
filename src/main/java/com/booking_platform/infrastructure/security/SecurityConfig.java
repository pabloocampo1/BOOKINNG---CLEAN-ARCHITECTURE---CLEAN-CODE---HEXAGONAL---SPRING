package com.booking_platform.infrastructure.security;

import com.booking_platform.infrastructure.security.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final CorsConfig corsConfig;
    private final String ROLE_GUEST = "GUEST";
    private final String ROLE_HOST = "HOST";
    private final JwtFilter jwtFilter;

    public SecurityConfig(CorsConfig corsConfig, JwtFilter jwtFilter) {
        this.corsConfig = corsConfig;
        this.jwtFilter = jwtFilter;
        
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> {
                    httpSecurityCorsConfigurer.configurationSource(corsConfig.corsConfigurationSource());
                })
                .authorizeHttpRequests(request -> {

                    request.requestMatchers(HttpMethod.POST, "/api/v1/users/become-host").hasRole(ROLE_HOST);
                    request.requestMatchers(HttpMethod.POST, "/api/v1/property").hasRole(ROLE_HOST);
                    request.requestMatchers(HttpMethod.POST, "/api/v1/property/*/availability/block").hasRole(ROLE_HOST);
                    request.requestMatchers("/api/v1/auth/**").permitAll();
                    request.anyRequest().authenticated();
                })
                .headers(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}

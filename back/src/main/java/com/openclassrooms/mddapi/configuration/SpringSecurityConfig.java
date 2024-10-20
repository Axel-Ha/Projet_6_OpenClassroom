package com.openclassrooms.mddapi.configuration;

import com.openclassrooms.mddapi.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for Spring Security.
 * <p>
 * This class configures security settings for the application, including HTTP
 * security, authentication,
 * and password encoding. It sets up a filter chain to handle security aspects
 * such as authentication and
 * authorization, and provides the necessary beans for password encoding and
 * authentication management.
 * </p>
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    /**
     * Configures the security filter chain for HTTP requests.
     * <p>
     * This method sets up security configurations, including disabling CSRF
     * protection,
     * permitting requests to certain endpoints, enforcing authentication for other
     * endpoints,
     * and adding the JWT authentication filter before the default
     * UsernamePasswordAuthenticationFilter.
     * </p>
     *
     * @param http the {@link HttpSecurity} object used to configure security
     *             settings
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .antMatchers(
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/swagger-resources",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api/auth/register",
                                "/api/auth/login"
                        ).permitAll())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}


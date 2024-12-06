package com.uexcel.jwt.config;

import com.uexcel.jwt.filter.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;


@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
       return http.csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(auth->auth
                       .requestMatchers("/h2-console/**").permitAll()
                       .requestMatchers("/swagger-ui/**","/v3/api-doc*/**").permitAll()
                       .requestMatchers("/api/create").permitAll()
                       .requestMatchers("/api/login","/error").permitAll()
                       .anyRequest().authenticated()
               )
               .headers(h->h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
               .userDetailsService(userDetailsService)
               .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
               .build();
    }

    @Bean
    public CompromisedPasswordChecker passwordChecker(){
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}



package com.uexcel.jwt.config;

import com.uexcel.jwt.filter.CSRFTokenFilter;
import com.uexcel.jwt.filter.JwtGeneratorFilter;
import com.uexcel.jwt.filter.JwtValidatorFilter;
import com.uexcel.jwt.service.impl.JwtUserDetailsService;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
       return http
               .csrf(csrfConfig->csrfConfig
                       .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                       .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                       .ignoringRequestMatchers("/h2-console/**","/login/**","/api/login")
                       .ignoringRequestMatchers("/swagger-ui/**","/v3/api-doc*/**")
               )

               .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
                   @Override
                   public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                       CorsConfiguration config = new CorsConfiguration();
                       config.setAllowedOrigins(Collections.singletonList("https://localhost:4200"));
                       config.setAllowedMethods(Collections.singletonList("*"));
                       config.setAllowCredentials(true);
                       config.setAllowedHeaders(Collections.singletonList("*"));
                       config.setExposedHeaders(Arrays.asList("Authorization"));
                       config.setMaxAge(3600L);
                       return config;
                   }
               }))

               .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .addFilterAfter( new JwtGeneratorFilter(), BasicAuthenticationFilter.class)
               .addFilterBefore( new JwtValidatorFilter(), BasicAuthenticationFilter.class)
               .addFilterAfter(new CSRFTokenFilter(), BasicAuthenticationFilter.class)

               .authorizeHttpRequests(auth->auth
                       .requestMatchers("/h2-console/**").permitAll()
                       .requestMatchers("/swagger-ui/**","/v3/api-doc*/**").permitAll()
                       .requestMatchers("/api/create").permitAll()
                       .requestMatchers("/api/login","/error","/login/**").permitAll()
                       .anyRequest().authenticated()
               )
               .httpBasic(withDefaults())
               .headers(h->h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
               .build();
    }

    @Bean
    public CompromisedPasswordChecker passwordChecker(){
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

    @Bean
    public PasswordEncoder passwordencoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(JwtUserDetailsService jwtUserDetailsService,
                                                       PasswordEncoder passwordEncoder) throws Exception {
        UsernamePasswordAuthenticationProvider uPAProvider =
                new UsernamePasswordAuthenticationProvider(jwtUserDetailsService, passwordEncoder);
        ProviderManager providerManager = new ProviderManager(uPAProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("My REST API")
                        .description("JWT API Demo.")
                        .version("1.0").contact(new Contact().name("Uexcel")
                                .email( "www.github.com").url("uexcel@gmail.com"))
                        .license(new License().name("License of API")
                                .url("API license URL")));
    }


    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}



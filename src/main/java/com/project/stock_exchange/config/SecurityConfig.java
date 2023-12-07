package com.project.stock_exchange.config;

import com.project.stock_exchange.config.service.MyUserDetailsService;
import com.project.stock_exchange.util.UnAuthorizedUserAuthenticationEntryPoint;
import com.project.stock_exchange.util.jwt.JWTFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@SuppressWarnings("ALL")
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private JWTFilter filter;
    @Autowired
    private UnAuthorizedUserAuthenticationEntryPoint authenticationEntryPoint;
    private String allowedMethods = "*";
    private String allowedHeaders = "*";
    private String corsConfiguration = "/**";
    private String allowedOrigin = "*";
    private boolean allowedCredentials = false;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .httpBasic().disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/dashboard/**", "/accounts/**", "/stocks/**").hasAnyRole("USER", "ADMIN")
                .and()
                .userDetailsService(myUserDetailsService)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)

//                .authenticationEntryPoint(
//                        (request, response, authException) ->
//                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
//                )
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigin));
        configuration.setAllowedMethods(Arrays.asList(allowedMethods));
        configuration.setAllowedHeaders(Arrays.asList(allowedHeaders));
        configuration.setAllowCredentials(allowedCredentials);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(corsConfiguration, configuration);
        return source;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
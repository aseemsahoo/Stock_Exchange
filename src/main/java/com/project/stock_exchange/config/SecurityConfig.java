package com.project.stock_exchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

//
////@SuppressWarnings("ALL")
////@Configuration
////@EnableWebSecurity
////public class SecurityConfig
////{
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                .csrf().disable()
////                .authorizeRequests()
////                .anyRequest().authenticated()
////                .and()
////                .httpBasic().disable()
////                .formLogin().disable();
////        return http.build();
////    }
//////    @Bean
//////    public UserDetailsManager UserDetailsManager(DataSource dataSource) {
//////        JdbcUserDetailsManager myUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//////
//////        myUserDetailsManager
//////                .setUsersByUsernameQuery("select username, password, enabled from users where username=?");
//////        myUserDetailsManager
//////                .setAuthoritiesByUsernameQuery("select username, role from roles where username=?");
//////        return myUserDetailsManager;
//////    }
////}
////
//
//@SuppressWarnings("ALL")
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        http
////                .csrf().disable()
////                .authorizeRequests()
////                .anyRequest().authenticated()
////                .and()
////                .httpBasic().disable()
////                .formLogin().disable()
//////                .authorizeHttpRequests((authz) -> authz
//////                        .anyRequest().authenticated()
//////                )
////                .httpBasic(withDefaults());
//
//        http
//                .cors().and()
//                .csrf().disable() // Disable CSRF for simplicity, enable it in production
//                .authorizeRequests()
//                .requestMatchers("/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic();
//
//        // Add the CORS filter before the Spring Security filter chain
//        http.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOrigin("*"); // Allow all origins
//        config.addAllowedMethod("*"); // Allow all HTTP methods
//        config.addAllowedHeader("*"); // Allow all headers
//        config.setAllowCredentials(true);
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
//}
////    @Bean
////    CorsConfigurationSource corsConfigurationSource() {
////        CorsConfiguration configuration = new CorsConfiguration();
////        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
////        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", configuration);
////        return source;
////    }
//
////
////}
//////@Configuration
//////
//////@EnableWebMvc
//////public class SecurityConfig implements WebMvcConfigurer {
//////    @Override
//////    public void configure(HttpSecurity http) throws Exception {
//////        http.csrf().disable().authorizeRequests()
//////                .antMatchers("/").permitAll()
//////                .antMatchers(HttpMethod.POST,"/newuser").permitAll()
//////                .antMatchers(HttpMethod.POST, "/login").permitAll()
//////                .antMatchers(HttpMethod.POST,"/newuser/*").permitAll()
//////                .antMatchers(HttpMethod.GET,"/master/*").permitAll()
//////                .antMatchers(HttpMethod.GET,"/exploreCourse").permitAll()
//////                .anyRequest().authenticated()
//////    }
//////
//////    @Override
//////    public void addCorsMappings(CorsRegistry registry) {
//////        registry.addMapping("/**");
//////    }
//////}


@SuppressWarnings("ALL")
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private String allowedMethods = "*";

    private String allowedHeaders = "*";

    private String corsConfiguration = "/**";

    private String allowedOrigin = "*";

    private boolean allowedCredentials = false;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(corsConfiguration)
                .permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
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
}
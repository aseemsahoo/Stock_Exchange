//package com.project.stock_exchange.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import javax.sql.DataSource;
//
//
//@SuppressWarnings("ALL")
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig
//{
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic().disable()
//                .formLogin().disable();
//        return http.build();
//    }
////    @Bean
////    public UserDetailsManager UserDetailsManager(DataSource dataSource) {
////        JdbcUserDetailsManager myUserDetailsManager = new JdbcUserDetailsManager(dataSource);
////
////        myUserDetailsManager
////                .setUsersByUsernameQuery("select username, password, enabled from users where username=?");
////        myUserDetailsManager
////                .setAuthoritiesByUsernameQuery("select username, role from roles where username=?");
////        return myUserDetailsManager;
////    }
//}

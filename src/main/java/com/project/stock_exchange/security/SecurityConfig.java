package com.project.stock_exchange.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig
{
    @Bean
    public UserDetailsManager UserDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager myUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        myUserDetailsManager
                .setUsersByUsernameQuery("select username, password, enabled from users where username=?");
        myUserDetailsManager
                .setAuthoritiesByUsernameQuery("select username, role from roles where username=?");
        return myUserDetailsManager;
    }
}

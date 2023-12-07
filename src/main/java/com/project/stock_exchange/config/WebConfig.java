package com.project.stock_exchange.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//@Configuration
//@EnableWebMvc
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
////                .allowedOrigins("http://localhost:3000")
//                .allowedOrigins("*")
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowCredentials(true);
//    }
//}


@Configuration
@EnableWebMvc
public class WebConfig {
    @Value("${allowedMethods}")
    private String allowedMethods;
    @Value("${allowedHeaders}")
    private String allowedHeaders;
    @Value("${corsConfiguration}")
    private String corsConfiguration;
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer(){
            public void addCorsMappings(final CorsRegistry registry){
                registry.addMapping(corsConfiguration).allowedHeaders(allowedHeaders).allowedMethods(allowedMethods);
            }
        };
    }
}
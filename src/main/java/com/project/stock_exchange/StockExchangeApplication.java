package com.project.stock_exchange;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={UserDetailsServiceAutoConfiguration.class})
public class StockExchangeApplication implements WebMvcConfigurer
{
	public void addViewControllers(ViewControllerRegistry registry)
	{
		registry.addViewController("/showCharts").setViewName("stocks/list-stock-chart");
	}

	public static void main(String[] args) {
		SpringApplication.run(StockExchangeApplication.class, args);
	}

	/*
	 these commands from below will be executed after spring beans(classes with @component)
	 have been loaded 
	*/
	@Bean
	public CommandLineRunner commandLineRunner()
	{
		return runner ->
		{
			System.out.println("HELLO");
		};
	}
}

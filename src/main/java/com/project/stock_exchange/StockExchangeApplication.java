package com.project.stock_exchange;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StockExchangeApplication {

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

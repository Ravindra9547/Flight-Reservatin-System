package com.frs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = { "com.frs" })
@EnableTransactionManagement
public class FlightresrvationsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightresrvationsystemApplication.class, args);
	}
}

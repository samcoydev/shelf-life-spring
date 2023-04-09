package com.samcodesthings.shelfliferestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class ShelfLifeRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShelfLifeRestApiApplication.class, args);
	}

}

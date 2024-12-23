package com.cms;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CanteenManagementSystemApplication {

	public static void main(String[] args) {
		System.out.println("Server started at " + new Date());
		SpringApplication.run(CanteenManagementSystemApplication.class, args);
	}

}

package com.solarize.solarizeWebBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SolarizeWebBackendApplication {

	public static void main(String[] args) {
		System.out.println("EMAIL -> " + System.getenv("EMAIL"));
		System.out.println("PASSWORD_EMAIL -> " + System.getenv("PASSWORD_EMAIL"));

		SpringApplication.run(SolarizeWebBackendApplication.class, args);
	}

}

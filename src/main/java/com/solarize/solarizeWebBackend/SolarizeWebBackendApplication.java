package com.solarize.solarizeWebBackend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class SolarizeWebBackendApplication implements CommandLineRunner {
    @Value("${app.env}")
    private String env;


	public static void main(String[] args) {
		SpringApplication.run(SolarizeWebBackendApplication.class, args);
	}

    @Override
    public void run(String... args) {
        System.out.println("Server running in the enviroment: " + env);
    }
}

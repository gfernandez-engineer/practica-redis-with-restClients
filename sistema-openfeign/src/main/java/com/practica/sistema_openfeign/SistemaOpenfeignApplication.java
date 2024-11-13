package com.practica.sistema_openfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SistemaOpenfeignApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaOpenfeignApplication.class, args);
	}

}

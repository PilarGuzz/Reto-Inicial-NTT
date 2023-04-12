package com.everis.language.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class RetoInicialConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetoInicialConfigServiceApplication.class, args);
	}

}

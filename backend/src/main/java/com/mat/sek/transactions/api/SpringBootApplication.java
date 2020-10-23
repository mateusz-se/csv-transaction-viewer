package com.mat.sek.transactions.api;

import com.mat.sek.transactions.api.upload.storage.StorageProperties;
import com.mat.sek.transactions.api.upload.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@org.springframework.boot.autoconfigure.SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class})
public class SpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}

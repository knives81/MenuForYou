package com.rest.menuforyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.rest.menuforyou.repository")
@SpringBootApplication
public class MenuForYouApplication {

	public static void main(String[] args) {
		SpringApplication.run(MenuForYouApplication.class, args);
	}
}

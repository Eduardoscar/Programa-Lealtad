package com.oegr.ProgramaLealtadPR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ProgramaLealtadPrApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgramaLealtadPrApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").exposedHeaders("*");
				//registry.addMapping("/login").allowedOrigins("http://localhost:8081").exposedHeaders("*");
			}
		};
	}
}

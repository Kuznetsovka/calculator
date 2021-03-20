package ru.systemairac.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CalculatorApplication implements WebMvcConfigurer {
	public static void main(String[] args) {
		SpringApplication.run(CalculatorApplication.class, args);
	}

}

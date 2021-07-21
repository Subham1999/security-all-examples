package com.subham.springsecurity.securityallexamples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SecurityAllExamplesApplication {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/auth");
		SpringApplication.run(SecurityAllExamplesApplication.class, args);
	}

}

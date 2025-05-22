package com.example.scraping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScrapingApplication {
	//curl -X POST http://localhost:8000/api/companies ^ -H "Content-Type: application/json" ^ -d "{\"name\": \"youtube\"}" {"id":2,"name":"youtube"}
	public static void main(String[] args) {
		SpringApplication.run(ScrapingApplication.class, args);
	}

}

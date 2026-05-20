package com.example.demo;

import com.example.demo.service.DataService;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FastXBackendApplication {




	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(FastXBackendApplication.class, args);
		DataService dataService = context.getBean(DataService.class);
		dataService.loadData();
	}



}

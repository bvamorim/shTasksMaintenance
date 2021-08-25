package com.sh.tasks.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sh.tasks.api.repository.dataloader.DataInitializer;

/**
* <h1>TasksApplication</h1>
* The main class, is required to run the Application.
*
* @author  Bruno Amorim
* @version 1.0
* @since   2021-08-22
*/
@SpringBootApplication
public class TasksApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasksApplication.class, args);
	}
	
	@Bean
	public String initializeData(DataInitializer dataInitializer) {
		dataInitializer.createRoles();
		dataInitializer.createUsers();
		
		return "";
	}
	
}

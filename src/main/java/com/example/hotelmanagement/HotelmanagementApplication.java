package com.example.hotelmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HotelmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelmanagementApplication.class, args);
	}

}

package com.book.my.show;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableCaching
@SpringBootApplication
public class BookMyShowApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookMyShowApplication.class, args);
	}

}

/*
@SpringBootApplication = @SpringBootConfiguration  + @EnableAutoConfiguration  + @ComponentScan
@SpringBootConfiguration = it will load all the properties defined in application.yml or properties file
@EnableAutoConfiguration = Automatically prolerties file configured with the beans
@ComponentScan:  Scan and load all beans
 */
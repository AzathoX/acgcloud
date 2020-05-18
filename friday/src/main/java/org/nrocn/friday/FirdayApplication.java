package org.nrocn.friday;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FirdayApplication {

	private  static  final  Logger LOGGER = LoggerFactory.getLogger(FirdayApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(FirdayApplication.class, args);
	}

}

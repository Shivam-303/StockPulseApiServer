package com.stockinsight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class StockPulseApplication {

	public static void main(String[] args) {

		SpringApplication.run(StockPulseApplication.class, args);
	}

	 //RestTemplate bean to be used in the StockService class it declared as
	// so that when ever RestTemplate is needed instead of creating a new instance it can use this@Bean
	// increases the reusability, testability and cleaner code also

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}


}

package com.weshopifyplatform.app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableMongoRepositories
@EnableFeignClients //SpringBoot dont create bean of the feignclient without this annotation
public class WeshopifyBrandsMgmtServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeshopifyBrandsMgmtServiceApplication.class, args);
	}
	
	//Imp to note how we have created a Bean of a class which we have not implemented 
	@Bean
	public ModelMapper modelMapper() {
			return new ModelMapper();
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}

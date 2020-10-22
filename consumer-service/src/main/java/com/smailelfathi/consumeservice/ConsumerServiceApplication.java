package com.smailelfathi.consumeservice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableFeignClients("com.smailelfathi.consumeservice")
@EnableDiscoveryClient
@RestController
public class ConsumerServiceApplication {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private QuoteExchangeServiceProxy proxy;

	@GetMapping("/quotes-feign/{quote}")
	public String currencyFeign(@PathVariable String quote) {

		String response = proxy.retrieveExchangeValue(quote);

		logger.info("{}", response);

		return response;
	}



	public static void main(String[] args) {
		SpringApplication.run(ConsumerServiceApplication.class, args);
	}

}

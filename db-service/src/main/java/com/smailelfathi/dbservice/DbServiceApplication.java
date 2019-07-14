package com.smailelfathi.dbservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;


@EnableEurekaClient
@EnableJpaRepositories(basePackages ="com.smailelfathi.dbservice.repository")
@SpringBootApplication
@EnableHystrix
@EnableCircuitBreaker
@RestController
public class DbServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbServiceApplication.class, args);
	}
	
	
	   @RequestMapping(value = "/")
	   @HystrixCommand(fallbackMethod = "fallback_hello", commandProperties = {
	      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
	   })
	   public String hello() throws InterruptedException {
		   if(Math.random()>.5) {
	      Thread.sleep(3000);
	      throw new RuntimeException("RuntimeException");
		   }
	      return "Welcome Hystrix";
	   }
	   
	   private String fallback_hello() {
	      return "Request fails. It takes long time to response";
	   }

}

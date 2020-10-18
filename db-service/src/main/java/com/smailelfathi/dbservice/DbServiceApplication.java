package com.smailelfathi.dbservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;


@EnableEurekaClient
@EnableJpaRepositories(basePackages ="com.smailelfathi.dbservice.repository")
@SpringBootApplication
//@EnableHystrix
//@EnableCircuitBreaker
@RestController
@RefreshScope
public class DbServiceApplication {
	
	
	@Value("${xParam:''}")
	private Boolean xParam;
	
	@Value("${copyRight:''}")
	private String copyRight;
	
	@Value("${spring.jpa.show-sql:true}")
	private Boolean showSQL;
	
	@GetMapping("/myConfig")
	public Map<String,Object> Myconfig(){
		 Map<String,Object> params=new HashMap<>();
		 params.put("xParam",xParam);
		 params.put("copyRight",copyRight);
		 params.put("showSQL",showSQL);

		return params;
	}
	
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

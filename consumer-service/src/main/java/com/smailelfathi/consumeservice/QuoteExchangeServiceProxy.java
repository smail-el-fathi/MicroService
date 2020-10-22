package com.smailelfathi.consumeservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="db-service",url = "localhost:8712")//ok
//@FeignClient(name="db-service")//ok
//@FeignClient(name="zuul-service",url = "localhost:8700/db-service")//ok
@FeignClient(name="zuul-service")
@RibbonClient(name="db-service")
public interface QuoteExchangeServiceProxy {

    @GetMapping("/db-service/rest/db/{quote}")
    public String retrieveExchangeValue
            (@PathVariable("quote") String quote);

}

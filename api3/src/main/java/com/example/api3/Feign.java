package com.example.api3;

import com.example.api3.paramEntity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "hello-service",fallback = HystrixTest.class)
public interface Feign {
    @RequestMapping("/api2/hello")
    String hello();

    @RequestMapping("/api2/get-json")
    User getUser(User u);
}

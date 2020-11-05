package com.example.api3;

import com.example.api3.paramEntity.User;
import org.springframework.stereotype.Component;

@Component
public class HystrixTest implements Feign{
    @Override
    public String hello() {
        return "this is fegin hystrix fallback";
    }

    @Override
    public User getUser(User u) {
        return null;
    }
}

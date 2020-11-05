package com.example.api3;

import com.example.api3.paramEntity.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Hello {
    private static final Logger logger = LoggerFactory.getLogger(Hello.class);
    @Autowired
    RestTemplate restTemplate;

    @Resource
    private Feign testFeigin;

    @Value("${yyx.status}")
    private int status;

    @Value("${demoTest}")
    private String demoTest;

    @LoadBalanced
    @Bean
    public RestTemplate rest() {
        return new RestTemplate();
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello3";
    }

    @RequestMapping("/fegin")
    public String test(){
        logger.info("test---------");
        return testFeigin.hello();
    }

    @HystrixCommand(fallbackMethod = "getDefaultTest")
    @RequestMapping("/rest-tpl")
    public String test1(){
        String data = restTemplate.getForObject("http://hello-service/api2/hello", String.class);
        return data;
    }

    public String getDefaultTest(){
        return "this is restTemplate hystrix fallback";
    }

    @PostMapping("/get-json")
    public User getJson(@RequestBody User u){
        return u;
    }

    @PostMapping("/get-json-list")
    public List<User> getJsonList(@RequestBody List<User> lu){
        return lu;
    }

    @RequestMapping("/list")
    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        User user1 = new User(1, "倪升武");
        User user2 = new User(2, "达人课");
        userList.add(user1);
        userList.add(user2);
        return userList;
    }

    @RequestMapping("/get-user")
    public User getUser(@RequestBody User u){
        return testFeigin.getUser(u);
    }

    @RequestMapping("/status")
    public int getStatusFromConfigServer(){
        return this.status;
    }

    @RequestMapping("/demo")
    public String getDemoTest(){
        return this.demoTest;
    }
}

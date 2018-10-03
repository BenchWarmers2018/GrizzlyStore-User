package com.benchwarmers.grads.grizzlystoreuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
public class GrizzlystoreUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrizzlystoreUserApplication.class, args);
        System.out.println("Hello user repo..");
    }
}

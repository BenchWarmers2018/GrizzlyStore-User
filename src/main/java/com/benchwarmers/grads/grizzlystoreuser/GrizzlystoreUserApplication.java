package com.benchwarmers.grads.grizzlystoreuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@SpringBootApplication
@EntityScan(basePackageClasses = {
        GrizzlystoreUserApplication.class,
        Jsr310JpaConverters.class
})


//@EnableEurekaClient
public class GrizzlystoreUserApplication {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(GrizzlystoreUserApplication.class, args);
        System.out.println("Hello user repo..");
    }
}

package com.lcl.eurakaclient2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class EurakaClient2Application {

    public static void main(String[] args) {
        SpringApplication.run(EurakaClient2Application.class, args);
    }

}

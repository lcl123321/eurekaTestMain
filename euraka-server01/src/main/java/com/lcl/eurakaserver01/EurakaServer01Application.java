package com.lcl.eurakaserver01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurakaServer01Application {

    public static void main(String[] args) {
        SpringApplication.run(EurakaServer01Application.class, args);
    }

}

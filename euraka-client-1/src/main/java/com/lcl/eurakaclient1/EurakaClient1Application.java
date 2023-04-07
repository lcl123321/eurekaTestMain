package com.lcl.eurakaclient1;

import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.lcl.eurakaclient1.dao")
public class EurakaClient1Application {

    private final static Logger logger = Logger.getLogger(EurakaClient1Application.class);

    public static void main(String[] args) {

        SpringApplication.run(EurakaClient1Application.class, args);
        logger.info("启动成功");
    }

}

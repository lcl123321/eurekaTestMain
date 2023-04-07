package com.lcl.eurakaclient2.controller;/*
 *@program:eurekaTestMain
 *@description:
 *@author: lcl
 *@Time: 2023/3/28  1:25
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @Value("${server.port}")
    String port;
    @RequestMapping("/hi")
    public String home(@RequestParam String name) {
        return "hi "+name+",i am from port:" +port;
    }

    @RequestMapping("/sayHello/{word}")
    public String sayHello(@PathVariable("word") String word){
        System.out.println("调用了sayHello-02接口");
        return "hello:"+word;
    }
}

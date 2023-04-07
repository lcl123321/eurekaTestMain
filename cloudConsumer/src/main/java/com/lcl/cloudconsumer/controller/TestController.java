package com.lcl.cloudconsumer.controller;/*
 *@program:eurekaTestMain
 *@description:
 *@author: lcl
 *@Time: 2023/3/28  2:34
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${helloService.servicePath}")
    private String serverPath;

    @Value("${helloService.servicePath.ribbon}")
    private String serverPathRibbon;

    @RequestMapping("/hello/{word}")
    public String hello(@PathVariable("word")String word) {
        //return restTemplate.getForObject(serverPath+word, String.class);//使用指定的url来调用服务，不常用
        String url = serverPathRibbon+"/sayHello/"+word;
        System.out.println("url = " + url);
        return restTemplate.getForObject(url, String.class);//使用ribbon来调用服务
    }

    @RequestMapping("/test/{word}")
    public String testHello(@PathVariable("word") String word){
        return helloByDiscoveryClient(word);
    }

    private String helloByDiscoveryClient(String world) {
        StringBuffer sb = null;
        //获取服务列表
        List<String> serviceIds = discoveryClient.getServices();
        if (CollectionUtils.isEmpty(serviceIds)) {
            System.out.println("注册中心无服务");
            return null;
        }
        //根据服务名获取服务
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("HelloServer");
        if (CollectionUtils.isEmpty(serviceInstances)) {
            System.out.println("未找到指定服务");
            return null;
        }
        ServiceInstance serviceInstance = serviceInstances.get(0);
        sb = new StringBuffer();
        sb.append("http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/sayHello/"+world);
        System.out.println("url="+sb);
        //发送get请求
        return restTemplate.getForObject(sb.toString(),String.class);
    }
}

package com.lcl.cloudconsumer.feignService;/*
 *@program:eurekaTestMain
 *@description:Feign客户端
 *@author: lcl
 *@Time: 2023/4/6  2:06
 */

import com.lcl.cloudconsumer.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "HelloServer",fallback = UserClientServiceFallbackFactory.class)
public interface UserClientService {
    @RequestMapping("/user/getUserById")
    Map getUserById(@RequestParam("id") Integer id);//注意，参数列表与服务提供方尽量一致，使用@RequestParam

    @RequestMapping("/user/getUserBean")
    Map getUserBeanAtFeign(@RequestBody User user);
}

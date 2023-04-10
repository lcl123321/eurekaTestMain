package com.lcl.cloudconsumer.controller;/*
 *@program:eurekaTestMain
 *@description:
 *@author: lcl
 *@Time: 2023/4/6  1:41
 */

import com.lcl.cloudconsumer.entity.User;
import com.lcl.cloudconsumer.feignService.UserClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Resource
    private UserClientService userClientService;

    @Value("${helloService.servicePath.ribbon}")
    private String serverPathRibbon;

    //未使用feign时的请求服务方式
    @RequestMapping("/getUserById")
    public String getUserById(Integer id){
        System.out.println("根据UserId查询");
        String url = serverPathRibbon + "/user/getUserById?id="+id;
        System.out.println("url = " + url);
        return restTemplate.getForObject(url,String.class);
    }

    @RequestMapping("/getUserByIdAtFeign")
    public Map getUserByIdAtFeign(Integer id){
        System.out.println("通过feign调用接口");
        HashMap<String,Object> map = new HashMap();

        map = (HashMap<String, Object>) userClientService.getUserById(id);
//        if (user == null){
//            return "errer";
//        }
        return map;
    }

    @RequestMapping("getUserBeanAtFeign")
    public Map getUserBeanAtFeign(@RequestBody User user){
        log.info("进入getUserBeanAtFeign");
        HashMap<String,Object> map = new HashMap();
        map =(HashMap<String, Object>) userClientService.getUserBeanAtFeign(user);
        return map;
    }


}

package com.lcl.cloudconsumer.feignService;/*
 *@program:eurekaTestMain
 *@description:统一处理熔断,UserClientService是Feign接口，所有访问都会走feign接口
 *@author: lcl
 *@Time: 2023/4/6  12:51
 */
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserClientServiceFallbackFactory implements UserClientService{

    @Override
    public Map getUserById(Integer id) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("msg","未找到Id为"+id+"的结果");
        map.put("user",null);
        return map;
    }
}

package com.lcl.springredis.controller;/*
 *@program:eurekaTestMain
 *@description:
 *@author: lcl
 *@Time: 2023/4/10  0:05
 */

import com.lcl.springredis.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("redis")
@RestController
public class RedisController {
    private final static Logger logger  = LoggerFactory.getLogger(RedisController.class);
    @Autowired
    private CacheService cacheService;

    @RequestMapping("setKeyAndValue")
    public String setKeyAndValue(String key,String value){
        logger.info("进入setKeyAndValue");
        cacheService.add(key,value);
        return "success";
    }
}

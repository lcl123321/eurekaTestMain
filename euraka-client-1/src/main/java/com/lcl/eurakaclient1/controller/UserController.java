package com.lcl.eurakaclient1.controller;/*
 *@program:eurekaTestMain
 *@description:
 *@author: lcl
 *@Time: 2023/4/6  0:39
 */

import com.lcl.eurakaclient1.entity.User;
import com.lcl.eurakaclient1.exception.NotFoundByUserId;
import com.lcl.eurakaclient1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    //使用Log4j进行日志输出，会保存到日志文件中
    private static org.apache.log4j.Logger log = Logger.getLogger(UserService.class);
    @Autowired
    private UserService userService;

    @RequestMapping("/getUserById")
    public Map getUserById(Integer id) throws NotFoundByUserId {
        log.info("根据ID从数据库中找用户");
        User user = userService.getUserById(id);
        HashMap<String,Object> map = new HashMap<>();
        if (user == null){
            throw new NotFoundByUserId();
        }
        map.put("msg","success");
        map.put("user",user);
        return map;
    }

    @RequestMapping("/getUserBean")
    public Map getUserBean(@RequestBody User user){
        log.info("进入了getUserBean");
        HashMap<String,Object> map = new HashMap<>();
        map.put("msg","success");
        map.put("user",user);
        return map;
    }

}

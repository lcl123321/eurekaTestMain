package com.lcl.eurakaclient1.service.impl;/*
 *@program:eurekaTestMain
 *@description:
 *@author: lcl
 *@Time: 2023/4/6  0:32
 */

import com.lcl.eurakaclient1.dao.UserMapper;
import com.lcl.eurakaclient1.entity.User;
import com.lcl.eurakaclient1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }
}

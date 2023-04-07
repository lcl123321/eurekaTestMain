package com.lcl.eurakaclient1.dao;/*
 *@program:eurekaTestMain
 *@description:
 *@author: lcl
 *@Time: 2023/4/6  0:29
 */

import com.lcl.eurakaclient1.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

     User getUserById(Integer id);

}

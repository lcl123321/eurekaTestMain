package com.lcl.cloudconsumer.controller;/*
 *@program:eurekaTestMain
 *@description:
 *@author: lcl
 *@Time: 2023/4/9  11:26
 */

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("view")
@Controller
public class ViewController {

    @RequestMapping("/index")
    public String index(HttpServletRequest request){
        System.out.println("进入了index");
        HttpSession session = request.getSession(true);
        session.setAttribute("name","张三");
        return "index";
    }

    @RequestMapping("/menu")
    public String menu(HttpServletRequest request){
        System.out.println("进入了menu");
        HttpSession session = request.getSession();
        String name = (String)session.getAttribute("name");
        System.out.println("name = " + name);
        return "menu";
    }
}

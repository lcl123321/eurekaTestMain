package com.lcl.eurakaclient1.util;/*
 *@program:eurekaTestMain
 *@description:
 *@author: lcl
 *@Time: 2023/3/27  11:39
 */

import com.lcl.eurakaclient1.entity.Employee;

import java.io.*;

public class SerializeDemo {
//    public static void main(String[] args) {
//        Employee employee = Employee.builder()
//                .setAddress("地址")
//                .setName("张三")
//                .setNumber(10)
//                .setSSN(15225)
//                .build();
//
//        try{
//            //File file = new File("D:\\ideaProjects\\eurekaTestMain\\euraka-client-1\\src\\main\\resources\\serializable\\employee.ser");
////            if (!file.exists()){
////                file.createNewFile();
////            }
//            FileOutputStream fileOutputStream = new FileOutputStream("serializable/employee.ser");
//            //FileOutputStream fileOutputStream = new FileOutputStream("D:\\ideaProjects\\eurekaTestMain\\euraka-client-1\\src\\main\\resources\\serializable\\employee.ser");
//            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
//            out.writeObject(employee);
//            out.close();
//            fileOutputStream.close();
//            System.out.println("结束!!!");
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        System.out.println(employee);
//    }

//    public static void main(String[] args) {
//        Employee employee = null;
//        try
//        {
//            FileInputStream fileIn = new FileInputStream("D:\\ideaProjects\\eurekaTestMain\\euraka-client-1\\src\\main\\resources\\serializable\\employee.ser");
//            //FileInputStream fileIn = new FileInputStream("D:\\ideaProjects\\eurekaTestMain\\euraka-client-1\\src\\main\\resources\\serializable\\employee.ser");
//            ObjectInputStream in = new ObjectInputStream(fileIn);
//            employee = (Employee) in.readObject();
//            in.close();
//            fileIn.close();
//        }catch(IOException i)
//        {
//            i.printStackTrace();
//            return;
//        }catch(ClassNotFoundException c)
//        {
//            System.out.println("Employee class not found");
//            c.printStackTrace();
//            return;
//        }
//        System.out.println(employee);
//    }
}

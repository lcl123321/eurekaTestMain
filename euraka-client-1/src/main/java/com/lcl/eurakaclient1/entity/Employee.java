package com.lcl.eurakaclient1.entity;/*
 *@program:eurekaTestMain
 *@description:链式创建对象
 *@author: lcl
 *@Time: 2023/3/27  11:33
 */

import lombok.Data;

import java.io.Serializable;

public class Employee implements Serializable
{
    private String name;
    private String address;
    private transient int SSN;//transient关键字是短暂的意思，对于transient 修饰的成员变量，在类的实例对象的序列化处理过程中会被忽略。 因此，transient变量不会贯穿对象的序列化和反序列化，生命周期仅存于调用者的内存中而不会写到磁盘里进行持久化。
    private int number;

    public Employee(){}

    public Employee(Builder builder){
        this.name = builder.name;
        this.address = builder.address;
        this.SSN = builder.SSN;
        this.number = builder.number;
    }

    public static Builder builder(){
        return new Builder();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSSN() {
        return SSN;
    }

    public void setSSN(int SSN) {
        this.SSN = SSN;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", SSN=" + SSN +
                ", number=" + number +
                '}';
    }

    public void mailCheck()
    {
        System.out.println("Mailing a check to " + name
                + " " + address);
    }


    public static class Builder{
        private String name;
        private String address;
        private transient int SSN;
        private int number;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setSSN(int SSN) {
            this.SSN = SSN;
            return this;
        }

        public Builder setNumber(int number) {
            this.number = number;
            return this;
        }

        public Employee build(){
            return new Employee(this);
        }
    }
}

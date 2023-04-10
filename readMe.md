eureka-server 8761
eureka-server01 8762
eureka-client-1 9001
eureka-client-2 9002
cloudConsumer 9903
springcloud-gateway 8087
spring-redis 8088





#### Spring Cloud Eureka的自我保护机制：

首先对Eureka注册中心需要了解的是Eureka各个节点都是平等的，没有ZK中角色的概念， 即使N-1个节点挂掉也不会影响其他节点的正常运行。默认情况下，如果Eureka Server在一定时间内（默认90秒）没有接收到某个微服务实例的心跳，Eureka Server将会移除该实例。但是当网络分区故障发生时，微服务与Eureka Server之间无法正常通信，而微服务本身是正常运行的，此时不应该移除这个微服务，所以引入了自我保护。
添加如下配置，关闭自我保护
eureka.server.enable-self-preservation=false

##### 客户配置：

心跳检测检测与续约时间

测试时将值设置设置小些，保证服务关闭后注册中心能及时踢出服务

lease-renewal-interval-in-seconds 每间隔1s，向服务端发送一次心跳，证明自己依然”存活“
lease-expiration-duration-in-seconds  告诉服务端，如果我2s之内没有给你发心跳，就代表我“死”了，将我踢出掉。
eureka.server.enable-self-preservation=false
eureka.client.registry-fetch-interval-seconds=1
eureka.instance.lease-renewal-interval-in-seconds=2

### spring-boot-starter-actuator

> 使用 spring-boot-starter-actuator 可以用于检测系统的健康情况、当前的Beans、系统的缓存等，具体可检测的内容参考下面的链接： https://docs.spring.io/spring-boot/docs/2.6.1/reference/htmlsingle/#actuator.endpoints.exposing

使用方法，在pom.xml 文件中引入**spring-boot-starter-actuator**依赖： 

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

默认情况下，通过web端只可访问http://localhost:8080/actuator/health ，可在application.properties中配置访问的uri、权限、端口等 

```
# 访问端口

management.server.port=8081

# 根路径

management.endpoints.web.base-path=/actuator/z

# web端允许的路径

management.endpoints.web.exposure.include=*
```

通过以上配置，开放了web端的所有访问，可通过访问http://localhost:8081/actuator/z/beans 来查看系统中的beans 



### Feign的使用

------

```
使用 Feign 能让编写的 WebService 客户端更加简洁，它的使用方法式定义一个接口，然后在上面添加注解。Spring Cloud 对 Feign 进行了封装，使其支持了 Spring MVC 标准注解和 HttpMessageConverters。Feign 可以与 Eureka 和 Ribbon 组合使用以支持负载均衡。因此，feign是在服务消费方使用的
```



#### 改造服务消费者

 在服务消费方添加Feign的依赖

```xml
<!-- feign的支持 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
```



修改启动类,添加@EnableFeignClients注解,启动类需要添加注解 @EnableFeignClients，表示启用 Feign 客户端

```java
package com.lcl.cloudconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients
public class CloudConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudConsumerApplication.class, args);
    }
}
```



创建Feign客户端

```java
import com.lcl.cloudconsumer.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "HelloServer")
public interface UserClientService {
    @RequestMapping("/user/getUserById")
    User getUserById(@RequestParam("id") Integer id);//注意，参数列表与服务提供方尽量一致，使用@RequestParam,在一致的情况下，服务提供方的@RequestParam可以省略
}
```

在Controller中引用

```java
@Autowired
private UserClientService userClientService;

@RequestMapping("/getUserByIdAtFeign")
public String getUserByIdAtFeign(Integer id){
    System.out.println("通过feign调用接口");
    User user = userClientService.getUserById(id);
    if (user == null){
        return "errer";
    }
    return user.toString();
}
```



### Feign客户端参数传递value

#### Feign消费服务时,以Get方式请求

如果想让服务消费者采用Get方式调用服务提供者,那么需要服务消费者的feign客户端的方法上,在所有简单类型的参数前加上@RequestParam注解。

如果传递的是JavaBean类型的参数, SpringCloud默认采用的是json的形式传递的,而SpringCloud传递json,采用的是post的提交方式,如果想让get请求,也能把JavaBean的参数传递给服务提供方, 在SpringBoot2.1.x版本中提供了**@SpringQueryMap**注解,可以传递对象参数，框架自动解析

![1680746482428](D:\ideaProjects\eurekaTestMain\assets\1680746482428.png)

#### Feign消费服务时,以Post方式请求

如果是简单数据类型,使用@RequestParam,如果是JavaBean类型,使用@RequestBody,但是注意,一个方法中,只能使用一个@RequestBody注解

![1680746547933](D:\ideaProjects\eurekaTestMain\assets\1680746547933.png)



### 服务容错保护Hystrix

> 多个微服务之间调用的时候，假设微服务A调用微服务B和微服务C，微服务B和微服务C又在调用其他的微服务，这就是所谓的“扇出”。如果扇出的链路上某个微服务的调用响应时间过长或者不可用，那么对微服务A的调用就会占用越来越多的系统资源，进而引起系统崩溃，这就是所谓的“雪崩效应”。
>
> 在分布式环境中,不可避免地会有许多服务依赖项中的某些服务失败而导致雪崩效应,Hystrix是一个库,可通过添加等待时间容限和容错逻辑来帮助您控制这些分布式服务之间的交互,Hystrix通过隔离服务之间的访问点,停止服务之间的级联故障并提供后备选项来实现此目的.所有这些都可以提高系统的稳定性.



#### 服务雪崩效应

什么是服务雪崩效应

> 服务雪崩效应是一种因服务提供者的不可用导致服务调用者的不可用，并将不可用逐渐放大的过程。

形成原因

- 服务提供者不可用

- 重试加大流量

- 服务调用者不可用

#### 实例

> 在服务调用方法新建一个 hystrix 处理类：UserClientServiceFallbackFactory，要实现UserClientService接口，其中 UserClientService 就是前面定义的 Feign 接口。而且我们的服务调用方都不需要添加Hystrix的依赖,因为Feign以及包含了Hystrix.
>
> 也就是说，把 hystrix 和 feign 绑起来，因为都是客户端的东西。通过 feign 去调用服务的时候，如果出问题了，就来执行我自定义的 hystrix 处理类中的方法，返回默认数据。

创建hystrix处理类

```java
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
```

UserClientService接口中添加fallback属性

```java
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "HelloServer",fallback = UserClientServiceFallbackFactory.class)
public interface UserClientService {
    @RequestMapping("/user/getUserById")
    Map getUserById(@RequestParam("id") Integer id);//注意，参数列表与服务提供方尽量一致，使用@RequestParam
}
```



修改配置文件,添加允许使用Hystrixes

```properties
#允许使用Hystrix
feign.hystrix.enabled=true
```

注意，如果controller中注入feign接口使用的是@Autowired

```java
@Autowired
private UserClientService userClientService;
```

要改成@Resource

```java
@Resource
private UserClientService userClientService;
```

因为hystrix处理类实现了feign接口，导致容器中出现同类型的多个bean



### 使用Zuul实现路由网关

#### 什么是网关(Gateway)

> 在微服务架构中，后端服务往往不直接开放给调用端，而是通过一个 API 网关根据请求的 URL，路由到相应的服务。当添加 API 网关后，在第三方调用端和服务提供方之间就创建了一面墙，这面墙直接与调用方通信进行权限控制，后将请求均衡分发给后台服务端。
>
> 包括负载均衡，统一鉴权，协议转换，监控监测等一系列功能



#### Spring Cloud Zuul

> Spring Cloud Zuul 路由是微服务架构的不可或缺的一部分，提供动态路由、监控、弹性、安全等的边缘服务。Zuul 是 Netflix 出品的一个基于JVM路由和服务端的负载均衡器。
>
> ​	Zuul 包含了对请求的路由和过滤两个最主要的功能。其中，路由功能负责将外部请求转发到具体的微服务实例上，是实现外部访问统一入口的基础。而过滤功能则是负责对请求的处理过程进行干预，是实现请求校验、服务聚合等功能的基础。
>
> Zuul 和 eureka 进行整合，将 zuul 自身注册为eureka服务治理下的应用，同时从eureka中获得其他微服务的消息，也即以后的访问微服务都是通过zuul跳转之后获得。负载均衡
>
> 所以，Zuul 提供：代理+路由+过滤三大功能。
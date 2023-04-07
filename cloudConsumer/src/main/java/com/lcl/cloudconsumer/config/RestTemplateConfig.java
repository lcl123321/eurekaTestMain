package com.lcl.cloudconsumer.config;/*
 *@program:eurekaTestMain
 *@description: 使用ribbon
 *@author: lcl
 *@Time: 2023/4/4  1:00
 */

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RetryRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    /**
     * '@LoadBalanced'注解表示使用Ribbon实现客户端负载均衡
     * 在方法上添加一个 @LoadBalanced 注解即可开启 Ribbon 负载均衡。
     * 这样就可以通过微服务的名字从 Eureka 中找到对应的服务并访问了。
     * @return RestTemplate
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    /**
     *' 指定其他负载均衡策略
     * @return IRule 注意版本问题，boot与cloud版本要对应，且只有相应的版本才能使用IRule
     */
    @Bean
    public IRule myRule() {
        // 指定重试策略：随机策略
        //return new RandomRule();

        //轮询策略
        //return new RoundRobinRule();

        //重试策略
//        其实就是轮询策略的增强版,先轮询，如果获取失败则在指定时间内重试，重新轮询可用的服务。
//        轮询策略服务不可用时不做处理,重试策略服务不可用时会重新尝试集群中的其他节点
        return new RetryRule();
    }
}

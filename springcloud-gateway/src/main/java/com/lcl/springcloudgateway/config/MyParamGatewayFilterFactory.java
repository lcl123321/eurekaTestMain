package com.lcl.springcloudgateway.config;/*
 *@program:eurekaTestMain
 *@description:
 *@author: lcl
 *@Time: 2023/4/9  13:52
 */

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MyParamGatewayFilterFactory extends AbstractGatewayFilterFactory<MyParamGatewayFilterFactory.Config> {

    public MyParamGatewayFilterFactory(){
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("param");//param对应Config的param
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // http://localhost:10010/user/8?name=yh config.param ==> name
            //获取请求参数中param对应的参数名 的参数值
            ServerHttpRequest request = exchange.getRequest();
            if (request.getQueryParams().containsKey(config.param)){
                request.getQueryParams().get(config.param).forEach((v) -> {
                    System.out.print("--局部过滤器--获得参数 "+config.param+"="+ v);
                });
            }
            return chain.filter(exchange);//执行请求
        };
    }

    //读取过滤器配置的参数
    public static class Config{
        //对应配置在application.yml配置文件中的过滤器参数名
        private String param;

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }
    }
}


package com.appsdeveloperblog.photoapp.api.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;


@Component
public class MyPreFilter implements GlobalFilter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(MyPreFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("it is ok ");

        String requestPath = exchange.getRequest().getPath().toString();
        ;logger.info("path is: "+requestPath);

        HttpHeaders headers = exchange.getRequest().getHeaders();

        Set<String> headerNames = headers.keySet();
        headerNames.forEach((headerName)->{
          String headValue =  headers.getFirst(headerName);
          logger.info("header: "+headValue);
        });

        return chain.filter(exchange );
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

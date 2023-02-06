package com.appsdeveloperblog.photoapp.api.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;


@Configuration
public class GlobalFilterConfiguration {

    final Logger log = LoggerFactory.getLogger(GlobalFilterConfiguration.class);

    @Order(1)
    @Bean
    public GlobalFilter secondPrefilter() {
        return (exchange, chain) -> {
            log.info("second pre filter exec...");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("second post filter exec...");
            }));
        };

    }
    @Order(2)
    @Bean
    public GlobalFilter thirdPrefilter() {
        return (exchange, chain) -> {
            log.info("third pre filter exec...");
            return chain.filter(exchange).then(Mono.fromRunnable(() ->{
                log.info("third post filter exec...");
            }));
        };
    }
    @Order(3)
    @Bean
    public GlobalFilter fourthPrefilter() {
        return (exchange, chain) -> {
            log.info("fourth pre filter exec...");
            return chain.filter(exchange).then(Mono.fromRunnable(() ->{
                log.info("fourth post filter exec...");
            }));
        };
    }



}

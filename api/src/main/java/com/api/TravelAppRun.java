package com.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.api.mapper")
@EnableScheduling
@SpringBootApplication
public class TravelAppRun {
    public static void main(String[] args) {
        SpringApplication.run(TravelAppRun.class,args);
    }
}

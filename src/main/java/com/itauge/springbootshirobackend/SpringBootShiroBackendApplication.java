package com.itauge.springbootshirobackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itauge.springbootshirobackend.dao")
public class SpringBootShiroBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootShiroBackendApplication.class, args);
    }

}

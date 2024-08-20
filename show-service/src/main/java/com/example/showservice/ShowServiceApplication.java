package com.example.showservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.example.showservice", "com.example.servicecommon"})
public class ShowServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShowServiceApplication.class, args);
    }

}

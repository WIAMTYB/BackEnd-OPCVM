package com.example.investservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InvestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvestServiceApplication.class, args);
    }

}

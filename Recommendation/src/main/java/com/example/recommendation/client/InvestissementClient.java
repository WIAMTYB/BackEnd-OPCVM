package com.example.recommendation.client;

import com.example.recommendation.dto.Investment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.recommendation.config.FeignConfig; // Importer la configuration Feign

import java.util.List;

@FeignClient(name = "Invest-service", url = "http://localhost:8082/api/invest", configuration = FeignConfig.class)
public interface InvestissementClient {

    @GetMapping("/user/{userId}")
    List<Investment> getInvestmentsByUser(@PathVariable Long userId);
}

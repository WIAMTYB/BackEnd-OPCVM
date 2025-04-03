package com.example.recommendation.client;

import com.example.recommendation.dto.Investment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "Invest-service", url = "http://localhost:8082/api/invest")
public interface InvestissementClient {
    @GetMapping("/user/{userId}")
    List<Investment> getInvestmentsByUser(@PathVariable Long userId);
}


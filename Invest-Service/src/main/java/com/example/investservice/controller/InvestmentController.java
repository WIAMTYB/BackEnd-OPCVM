package com.example.investservice.controller;

import com.example.investservice.entities.Investment;
import com.example.investservice.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invest")
public class InvestmentController {
    @Autowired
    private InvestmentService investmentService;
    //Pour lenregistrement d'un investissement
    @PostMapping("/save/user")
    public ResponseEntity<?> saveInvestment(@RequestParam Long userId, @RequestParam Double amount) {
        boolean clientExists = investmentService.checkIfClientExists(userId);

        if (!clientExists) {
            return ResponseEntity.status(404).body("Client non trouvé !");
        }

        // Crée l'investissement si l'utilisateur existe
        Investment investment = investmentService.saveInvestment(userId, amount);
        return ResponseEntity.ok(investment);
    }

    //lister linestissement selon userID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Investment>> getInvestmentsByUser(@PathVariable Long userId) {
        List<Investment> investments = investmentService.getInvestmentsByUser(userId);
        return ResponseEntity.ok(investments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInvestmentById(@PathVariable Long id) {
        Investment investment = investmentService.getInvestmentById(id);
        if (investment == null) {
            return ResponseEntity.status(404).body("Investissement non trouvé !");
        }
        return ResponseEntity.ok(investment);
    }
}




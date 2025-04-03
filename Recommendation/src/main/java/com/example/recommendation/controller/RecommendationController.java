package com.example.recommendation.controller;

import com.example.recommendation.client.InvestissementClient;
import com.example.recommendation.dto.Investment;
import com.example.recommendation.service.OpcvmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    @Autowired
    private OpcvmService opcvmService;

    @Autowired
    private InvestissementClient investissementClient;

    @GetMapping("/{userId}")
    public Map<String, BigDecimal> recommanderOpcvm(@PathVariable Long userId) {
        // Récupérer la liste des investissements de l'utilisateur
        List<Investment> investments = investissementClient.getInvestmentsByUser(userId);

        // Calculer le montant total investi
        BigDecimal montantTotalInvesti = investments.stream()
                .map(Investment::getAmount) // Récupérer chaque montant
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Faire la somme totale

        // Récupérer les rendements des OPCVM
        Map<String, BigDecimal> rendements = opcvmService.calculerRendements();

        // Appliquer les recommandations basées sur le montant total investi
        return rendements.entrySet().stream()
                .filter(entry -> montantTotalInvesti.compareTo(BigDecimal.valueOf(1000)) >= 0)
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(10) // Garde les 10 meilleurs OPCVMs
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}


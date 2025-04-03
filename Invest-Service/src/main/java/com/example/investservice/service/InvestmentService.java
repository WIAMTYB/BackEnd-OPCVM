package com.example.investservice.service;
import com.example.investservice.dto.ClientDTO;
import com.example.investservice.entities.Investment;
import com.example.investservice.repositories.InvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvestmentService {
    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private RestTemplate restTemplate;


    public Investment saveInvestment(Long userId, Double amount) {
        Investment investment = new Investment();
        investment.setUserId(userId);
        investment.setAmount(amount);
        investment.setCreatedAt(LocalDateTime.now());

        return investmentRepository.save(investment);
    }

    public List<Investment> getInvestmentsByUser(Long userId) {
        return investmentRepository.findByUserId(userId);
    }

    public Investment getInvestmentById(Long id) {
        return investmentRepository.findById(id).orElse(null);
    }


    // Vérification si l'utilisateur existe avant de créer l'investissement
    public boolean checkIfClientExists(Long userId) {
        String clientServiceUrl = "http://localhost:8081/api/client/" + userId;  // URL du microservice Client
        try {
            ClientDTO client = restTemplate.getForObject(clientServiceUrl, ClientDTO.class);
            return client != null;  // Si le client est trouvé, alors l'investissement peut être créé
        } catch (Exception e) {
            return false; // Si une erreur se produit (client non trouvé), retour false
        }
    }
}


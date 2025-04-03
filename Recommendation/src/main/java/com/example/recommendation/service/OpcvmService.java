package com.example.recommendation.service;

import com.example.recommendation.entities.Opcvm;
import com.example.recommendation.repositories.OpcvmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OpcvmService {

    @Autowired
    private OpcvmRepository opcvmRepository;

    public Map<String, BigDecimal> calculerRendements() {
        List<Opcvm> allOpcvm = opcvmRepository.findAll();

        return allOpcvm.stream()
                .collect(Collectors.groupingBy(
                        Opcvm::getCode,
                        Collectors.collectingAndThen(Collectors.toList(), this::calculerRendement))
                );
    }

    private BigDecimal calculerRendement(List<Opcvm> opcvmList) {
        if (opcvmList.isEmpty()) return BigDecimal.ZERO;

        opcvmList.sort((o1, o2) -> o1.getDatePrix().compareTo(o2.getDatePrix()));

        BigDecimal navInitial = opcvmList.get(0).getNav();
        BigDecimal navFinal = opcvmList.get(opcvmList.size() - 1).getNav();

        return navFinal.subtract(navInitial)
                .divide(navInitial, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));


    }
    // Ici c'est la formule classique de calcul du rendement for the total gain
    // We can use another formula which calculate linvestissement  avec combien il a progresse chaque annee
}


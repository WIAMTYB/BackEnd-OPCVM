package com.example.recomservice.service;

import com.example.recomservice.dto.OpcvmPerformanceDto;
import com.example.recomservice.entities.Opcvm;
import com.example.recomservice.repositories.OpcvmRepository;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OpcvmService {

    @Autowired
    private OpcvmRepository opcvmRepository;

    public Map<String, OpcvmPerformanceDto> calculerRendementsEtMediane() {
        List<Opcvm> allOpcvm = opcvmRepository.findAll();

        // Grouper par code OPCVM
        Map<String, List<Opcvm>> groupedByCode = allOpcvm.stream()
                .filter(opcvm -> opcvm.getDatePrix() != null && opcvm.getDatePrix().getYear() != 1900)
                .collect(Collectors.groupingBy(Opcvm::getCode));

        Map<String, OpcvmPerformanceDto> result = new HashMap<>();

        for (Map.Entry<String, List<Opcvm>> entry : groupedByCode.entrySet()) {
            String code = entry.getKey();
            List<Opcvm> opcvmList = entry.getValue();

            // Groupement par année
            Map<Integer, List<Opcvm>> byYear = opcvmList.stream()
                    .collect(Collectors.groupingBy(o -> o.getDatePrix().getYear()));

            Map<Integer, BigDecimal> rendementsParAnnee = new HashMap<>();

            for (Map.Entry<Integer, List<Opcvm>> yearEntry : byYear.entrySet()) {
                Integer year = yearEntry.getKey();
                List<Opcvm> yearlyList = yearEntry.getValue();
                BigDecimal rendement = calculerRendementAnnuel(yearlyList);
                rendementsParAnnee.put(year, rendement);
            }



            // Calcul de la médiane des rendements annuels
            double[] rendementValues = rendementsParAnnee.values().stream()
                    .mapToDouble(BigDecimal::doubleValue)
                    .toArray();

            BigDecimal mediane = BigDecimal.valueOf(new Median().evaluate(rendementValues));

            // Ajouter au résultat final
            result.put(code, new OpcvmPerformanceDto(rendementsParAnnee, mediane));
        }

        return result;
    }

    private BigDecimal calculerRendementAnnuel(List<Opcvm> opcvmList) {
        if (opcvmList.isEmpty()) return BigDecimal.ZERO;

        opcvmList.sort(Comparator.comparing(Opcvm::getDatePrix));

        BigDecimal navInitial = opcvmList.get(0).getNav();
        BigDecimal navFinal = opcvmList.get(opcvmList.size() - 1).getNav();

        if (navInitial.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;

        return navFinal.subtract(navInitial)
                .divide(navInitial, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }


}

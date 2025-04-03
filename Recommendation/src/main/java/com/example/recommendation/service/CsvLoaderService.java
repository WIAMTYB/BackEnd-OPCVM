package com.example.recommendation.service;
import com.example.recommendation.entities.Opcvm;
import com.example.recommendation.repositories.OpcvmRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvLoaderService {

    @Autowired
    private OpcvmRepository opcvmRepository;

    @PostConstruct
    @Transactional
    public void loadCsvData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Format correct pour la date

        try (Reader reader = new InputStreamReader(
                new ClassPathResource("DATA_202503121459.csv").getInputStream(), StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(';'))) {

            List<Opcvm> opcvmList = new ArrayList<>();

            for (CSVRecord record : csvParser) {
                try {
                    Opcvm opcvm = new Opcvm();
                    opcvm.setCode(record.get("PROVIDERPRICEDVALUECODE_"));
                    opcvm.setProvider(record.get("PROVIDER$CODE_"));
                    opcvm.setDatePrix(LocalDate.parse(record.get("PRICEDATE"), formatter));

                    // Vérification et conversion sécurisée de NAV_
                    String navStr = record.get("NAV_").replace(",", ".").trim(); // Remplacer virgule par point
                    if (!navStr.isEmpty() && navStr.matches("-?\\d+(\\.\\d+)?")) { // Vérifier si c'est un nombre valide
                        opcvm.setNav(new BigDecimal(navStr));
                    } else {
                        System.out.println(" Valeur NAV invalide : " + navStr);
                        opcvm.setNav(BigDecimal.ZERO); // Mettre une valeur par défaut si invalide
                    }

                    opcvmList.add(opcvm);
                } catch (Exception e) {
                    System.out.println("Erreur lors du parsing d'une ligne : " + record);
                    e.printStackTrace();
                }
            }

            opcvmRepository.saveAll(opcvmList);
            System.out.println("Données chargées avec succès !");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

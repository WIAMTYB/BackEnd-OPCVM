package com.example.recomservice.controller;
import com.example.recomservice.dto.OpcvmPerformanceDto;
import com.example.recomservice.repositories.OpcvmRepository;
import com.example.recomservice.service.OpcvmService;
import jakarta.servlet.http.HttpSession; // Import HttpSession
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/opcvm")
public class RecommendationController {

    @Autowired
    private OpcvmService opcvmService;

    @Autowired
    private OpcvmRepository opcvmRepository;


    @GetMapping("/noms-codes")
    public ResponseEntity<List<Map<String, String>>> getOpcvmNomsEtCodes(HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId != null) {
            List<Map<String, String>> result = opcvmRepository.findAll().stream()
                    .filter(opcvm -> opcvm.getNom() != null && opcvm.getCode() != null)
                    .distinct()
                    .map(opcvm -> Map.of("nom", opcvm.getNom(), "code", opcvm.getCode()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }



    @GetMapping("/rendements")
    public ResponseEntity<Map<String, OpcvmPerformanceDto>> getRendementsEtMediane(HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId != null) {
            return ResponseEntity.ok(opcvmService.calculerRendementsEtMediane());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/{code}/performance")
    public ResponseEntity<OpcvmPerformanceDto> getOpcvmPerformance(
            @PathVariable String code, HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId != null) {
            Map<String, OpcvmPerformanceDto> performances = opcvmService.calculerRendementsEtMediane();
            if (performances.containsKey(code)) {
                return ResponseEntity.ok(performances.get(code));
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


}
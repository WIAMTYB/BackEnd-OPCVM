package com.example.recommendation.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "opcvm")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Opcvm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code; // Code de l'OPCVM
    private String provider; // Fournisseur
    private LocalDate datePrix; // Date du prix
    private BigDecimal nav; // Valeur liquidative (NAV)
}


package com.example.investservice.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "investments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // Référence à l'utilisateur qui a investi
    @Min(value = 100, message = "Le montant minimum d'investissement est 100")
    private Double amount;

    private LocalDateTime createdAt;
}


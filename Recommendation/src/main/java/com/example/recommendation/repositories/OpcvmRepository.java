package com.example.recommendation.repositories;

import com.example.recommendation.entities.Opcvm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpcvmRepository extends JpaRepository<Opcvm, Long> {
    List<Opcvm> findByCode(String code);
}


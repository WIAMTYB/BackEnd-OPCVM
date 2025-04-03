package com.example.investservice.dto;


public class ClientDTO {

    private Long id;
    private String name;  // Ajoute ici les attributs nécessaires à ton Client
    private String email; // Exemple d'attribut

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


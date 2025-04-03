package com.example.clientservice.controller;


import com.example.clientservice.entities.Client;
import com.example.clientservice.repositories.ClientRepository;
import com.example.clientservice.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    ClientRepository clientRepository;


    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Client client) {
        Client newUser = clientService.registerClient(client);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public Optional<Client> loginUser(@RequestParam String email, @RequestParam String password) {
        Optional<Client> client = clientRepository.findByEmail(email);

        System.out.println(" Email recherché : " + email);
        System.out.println(" Résultat de la requête : " + (client.isPresent() ? client.get() : "Aucun utilisateur trouvé"));

        return client.filter(c -> {
            System.out.println(" Password en base : " + c.getPassword());
            System.out.println(" Password reçu : " + password);
            return c.getPassword().equals(password);
        });

    }
}

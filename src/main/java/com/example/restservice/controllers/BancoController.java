package com.example.restservice.controllers;

import com.example.restservice.services.BancoService;
import com.example.restservice.entities.Option;
import com.example.restservice.repositories.OptionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BancoController {

    private final BancoService bancoService;

    public BancoController(OptionRepository optionRepository) {
        this.bancoService = new BancoService(optionRepository);
    }

    @GetMapping("/options")
    public Iterable<Option> options() {
        return this.bancoService.getOptions();
    }
}

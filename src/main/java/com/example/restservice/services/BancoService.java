package com.example.restservice.services;

import com.example.restservice.entities.Option;
import com.example.restservice.repositories.OptionRepository;

public class BancoService {
    private final OptionRepository optionRepository;

    public BancoService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public Iterable<Option> getOptions() {
        return optionRepository.findAll();
    }
}

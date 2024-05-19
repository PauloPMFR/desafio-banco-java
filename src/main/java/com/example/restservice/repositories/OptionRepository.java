package com.example.restservice.repositories;

import com.example.restservice.entities.Option;
import org.springframework.data.repository.CrudRepository;

public interface OptionRepository extends CrudRepository<Option, Integer> {
}

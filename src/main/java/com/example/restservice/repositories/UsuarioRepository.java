package com.example.restservice.repositories;

import com.example.restservice.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    @Query(value = "select u from Usuario u where u.conta = ?1")
    Usuario findByConta(String conta);
}


package com.example.restservice.services;

import com.example.restservice.entities.Usuario;
import com.example.restservice.repositories.UsuarioRepository;
import com.example.restservice.repositories.UsuarioRepository;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Iterable<Usuario> getUsuario() {
        return usuarioRepository.findAll();
    }
}


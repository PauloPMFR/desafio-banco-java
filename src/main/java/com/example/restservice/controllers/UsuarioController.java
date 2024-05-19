package com.example.restservice.controllers;

import com.example.restservice.entities.Usuario;
import com.example.restservice.repositories.UsuarioRepository;
import com.example.restservice.services.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioService = new UsuarioService(usuarioRepository);
    }

    @GetMapping("/usuario")
    public Iterable<Usuario> Usuario() {
        return this.usuarioService.getUsuario();
    }
}

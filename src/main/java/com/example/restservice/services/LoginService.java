package com.example.restservice.services;

import com.example.restservice.entities.Usuario;
import com.example.restservice.models.CredenciasModel;
import com.example.restservice.repositories.UsuarioRepository;
import com.example.restservice.repositories.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoginService {
    private final UsuarioRepository usuarioRepository;

    public LoginService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario login(CredenciasModel credencias) throws Exception {
        Usuario usuario = this.usuarioRepository.findByConta(credencias.getConta());
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Suas Credencias são inválidas " );

        }
        if (usuario.getConta().equals(credencias.getConta()) && usuario.getSenha().equals(credencias.getSenha())) {

            return usuario;
        }


        throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Suas Credencias são inválidas  ", null);


    }
}

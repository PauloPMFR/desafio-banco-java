package com.example.restservice.controllers;
import com.example.restservice.entities.Usuario;
import com.example.restservice.models.CredenciasModel;
import com.example.restservice.repositories.UsuarioRepository;
import com.example.restservice.services.LoginService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(UsuarioRepository usuarioRepository) {
        this.loginService = new LoginService(usuarioRepository);
    }


    @PostMapping("")
    @ResponseBody
    public Usuario login( @RequestBody CredenciasModel credencias) throws Exception {
            return this.loginService.login(credencias);

    }
}
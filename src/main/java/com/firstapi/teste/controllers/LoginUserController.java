package com.firstapi.teste.controllers;

import com.firstapi.teste.models.UserModel;
import com.firstapi.teste.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/v1/api/teste")
public class LoginUserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Object> postUser(@RequestBody UserModel userModel) {
        try {
            if (userRepository.existsByLogin(userModel.getLogin())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Login já existe.");
            }
            userRepository.save(userModel);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar usuário.");
        }
    }

    @GetMapping
    public ResponseEntity<Object> login(
            @RequestParam(name = "login") String login,
            @RequestParam(name = "password") String password) {

        try {
            UserModel usuarioEncontrado = userRepository.findByLogin(login);

            if (usuarioEncontrado != null) {
                // Comparação direta das senhas
                if (usuarioEncontrado.getPassword().equals(password)) {
                    return ResponseEntity.ok(usuarioEncontrado);
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password Incorrect");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Not Found");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor");
        }
    }
    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers() {
        try {
            // Busca todos os usuários do banco de dados
            List<UserModel> usuarios = userRepository.findAll();
            return ResponseEntity.ok(usuarios); // Retorna a lista em formato JSON
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar usuários");
        }
    }

}

package com.example.duett_api.controllers;

import com.example.duett_api.dto.LoginRequestDTO;
import com.example.duett_api.dto.RegisterDTO;
import com.example.duett_api.dto.ResponseLoginDTO;
import com.example.duett_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        try{
            if(body.email() == null || body.password() == null){
                return new ResponseEntity<>("Por favor, preencha todos os campos", HttpStatus.BAD_REQUEST);
            }

            Object result = authService.login(body);
            if (result instanceof ResponseLoginDTO) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO body){
        try{
            if(body.name() == null ||body.email() == null || body.password() == null || body.cpf() == null || body.profile() == null){
                return new ResponseEntity<>("Por favor, preencha todos os campos", HttpStatus.BAD_REQUEST);
            }

            Object register = authService.register(body);
            if (register instanceof ResponseLoginDTO) {
                return ResponseEntity.ok(register);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(register);
        }catch (Exception e) {
            return new ResponseEntity<>("Erro interno no servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}


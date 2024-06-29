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
        Object result = authService.login(body);

        if (result instanceof ResponseLoginDTO) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO body){
        Object register = authService.register(body);

        if (register instanceof ResponseLoginDTO) {
            return ResponseEntity.ok(register);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(register);
    }

}


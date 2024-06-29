package com.example.duett_api.controllers;

import com.example.duett_api.domain.user.User;
import com.example.duett_api.dto.LoginRequestDTO;
import com.example.duett_api.dto.RegisterDTO;
import com.example.duett_api.dto.ResponseLoginDTO;
import com.example.duett_api.infra.security.TokenService;
import com.example.duett_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not fond"));

        if( passwordEncoder.matches(body.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseLoginDTO(user.getName(), user.getProfile(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO body){
        if (! this.repository.findByEmail(body.email()).isEmpty() || ! this.repository.findByCpf(body.cpf()).isEmpty()){
            return new ResponseEntity<>("Usuario ja cadastrado", HttpStatus.BAD_REQUEST);
        }

        User newUser = new User();

        newUser.setName(body.name());
        newUser.setEmail(body.email());
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setCpf(body.cpf());
        newUser.setProfile(body.profile());
        this.repository.save(newUser);

        String token = this.tokenService.generateToken(newUser);
        return ResponseEntity.ok(new ResponseLoginDTO(newUser.getName(), newUser.getProfile(), token));

    }

}


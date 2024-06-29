package com.example.duett_api.controllers;

import com.example.duett_api.domain.user.User;
import com.example.duett_api.dto.ChangePasswordDto;
import com.example.duett_api.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> getAllUsers(@RequestBody ChangePasswordDto body) {
        User user = this.userRepository.findById(body.id()).orElseThrow(() -> new RuntimeException("User not fond"));

        if (!this.passwordEncoder.matches(body.old_password(), user.getPassword()) ){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Senha incorreta");
        }

        String new_password = this.passwordEncoder.encode(body.new_password());
        user.setPassword(new_password);
        this.userRepository.save(user);
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }
}

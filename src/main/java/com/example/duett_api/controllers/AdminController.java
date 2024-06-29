package com.example.duett_api.controllers;

import com.example.duett_api.domain.user.User;
import com.example.duett_api.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable String id) {
        if (userRepository.findById(id).isEmpty()){
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("Usuario excluido com sucesso!");
    }
}

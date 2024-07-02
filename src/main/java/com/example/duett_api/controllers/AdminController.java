package com.example.duett_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.duett_api.domain.user.User;
import com.example.duett_api.repositories.UserRepository;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<Page<User>> getAllUsers( @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable =  PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);
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

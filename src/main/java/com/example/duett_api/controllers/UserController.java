package com.example.duett_api.controllers;

import com.example.duett_api.dto.ChangePasswordDto;
import com.example.duett_api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto body) {
        String result = userService.changePassword(body);

        switch (result) {
            case "Senha alterada com sucesso!":
                return ResponseEntity.ok(result);
            case "Usuario não encontrado":
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            case "Senha incorreta":
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            default:
                return new ResponseEntity<>("Erro ao atualizar senha", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.example.duett_api.controllers;

import com.example.duett_api.dto.ChangePasswordDto;
import com.example.duett_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @Operation(summary = "Realiza login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso!", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ChangePasswordDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Por favor, preencha todos os campos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar senha", content = @Content)
    })
    @PostMapping("/change-password")
    @SecurityRequirement(name = "bearer-jwt")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto body) {
        try{
            if(body.id() == null || body.old_password() == null|| body.new_password() == null ){
                return new ResponseEntity<>("Por favor, preencha todos os campos", HttpStatus.BAD_REQUEST);
            }

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
        }catch (Exception e) {
            return new ResponseEntity<>("Erro interno no servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.example.duett_api.controllers;

import com.example.duett_api.domain.user.User;
import com.example.duett_api.dto.LoginRequestDTO;
import com.example.duett_api.dto.RegisterDTO;
import com.example.duett_api.dto.ResponseLoginDTO;
import com.example.duett_api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Realiza login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = LoginRequestDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Por favor, preencha todos os campos", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não encontrado ou senha incorreta", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
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
    @Operation(summary = "Cadastro de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Por favor, preencha todos os campos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content),
    })
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


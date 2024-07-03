package com.example.duett_api.controllers;

import com.example.duett_api.domain.user.User;
import com.example.duett_api.repositories.UserRepository;
import com.example.duett_api.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(UserRepository userRepository, AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "Retorna uma lista de usuários cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @GetMapping("/users")
    public ResponseEntity<Page<User>> getAllUsers( @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<User> users = this.adminService.getAllUsers(page, size);
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Remove um usuarios da lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable String id) {
        try {
            String deleteResponse = this.adminService.deleteUserById(id);
            if(deleteResponse.equals("not_found")){
                return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok(deleteResponse);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.example.duett_api.service;

import com.example.duett_api.domain.user.User;
import com.example.duett_api.dto.LoginRequestDTO;
import com.example.duett_api.dto.RegisterDTO;
import com.example.duett_api.dto.ResponseLoginDTO;
import com.example.duett_api.infra.security.TokenService;
import com.example.duett_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public Object login(LoginRequestDTO body){
        User user = this.repository.findByEmail(body.email()).orElse(null);

        if (user == null) {
            return "Usuário não encontrado.";
        }
        if (! passwordEncoder.matches(body.password(), user.getPassword())){
            return "Senha incorreta.";
        }

        String token = tokenService.generateToken(user);
        return  new ResponseLoginDTO( user.getId(), user.getName(), user.getProfile(), token );
    }

    public Object register(RegisterDTO body){
        if (! this.repository.findByEmail(body.email()).isEmpty() || ! this.repository.findByCpf(body.cpf()).isEmpty()){
            return "Usuario ja cadastrado.";
        }

        if (isEmptyOrNull(body.name()) || isEmptyOrNull(body.email()) ||isEmptyOrNull(body.password())|| isEmptyOrNull(body.cpf()) || isEmptyOrNull(body.profile())){
            return  ("Por favor, preencha todos os campos.");
        }

        User newUser = new User();
        newUser.setName(body.name());
        newUser.setEmail(body.email());
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setCpf(body.cpf());
        newUser.setProfile(body.profile());

        this.repository.save(newUser);
        String token = this.tokenService.generateToken(newUser);

        return new ResponseLoginDTO( newUser.getId(), newUser.getName(), newUser.getProfile(), token );
    }

    private boolean isEmptyOrNull(String value) {
        return value == null || value.isEmpty();
    }

}

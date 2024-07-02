package com.example.duett_api.service;

import com.example.duett_api.domain.user.User;
import com.example.duett_api.dto.ChangePasswordDto;
import com.example.duett_api.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String changePassword(ChangePasswordDto body) {
        Optional<User> optionalUser = userRepository.findById(body.id());

        if (optionalUser.isEmpty()) {
            return "Usuario não encontrado";
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(body.old_password(), user.getPassword())) {
            return "Senha incorreta";
        }

        String newPassword = passwordEncoder.encode(body.new_password());
        user.setPassword(newPassword);
        userRepository.save(user);

        return "Senha alterada com sucesso!";
    }
}

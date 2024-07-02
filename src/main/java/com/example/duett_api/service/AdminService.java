package com.example.duett_api.service;

import com.example.duett_api.domain.user.User;
import com.example.duett_api.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final UserRepository userRepository;

    AdminService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Page<User>  getAllUsers(int page, int size) {
        Pageable pageable =  PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);
        return  users;
    }

    public String deleteUserById(String id) {
        if (userRepository.findById(id).isEmpty()){
            return  "not_found";
        }
        userRepository.deleteById(id);
        return "Usuario excluido com sucesso!";
    }
}

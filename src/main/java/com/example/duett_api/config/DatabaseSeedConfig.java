package com.example.duett_api.config;

import com.example.duett_api.domain.user.User;
import com.example.duett_api.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("dev")
public class DatabaseSeedConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void seedDatabase() {
        if (userRepository.count() == 0) {
            List<User> users = Arrays.asList(
                new User(null, "David Serrate", "111.111.111-11", "admin@admin", passwordEncoder.encode("admin"), "ADMIN"),
                new User(null, "Jane Smith", "222.222.222-22", "jane.smith@example.com", passwordEncoder.encode("password123"), "ADMIN"),
                new User(null, "Alice Johnson", "333.333.333-33", "alice.johnson@example.com", passwordEncoder.encode("password123"), "USER"),
                new User(null, "Bob Brown", "444.444.444-44", "bob.brown@example.com", passwordEncoder.encode("password123"), "USER"),
                new User(null, "Charlie Davis", "555.555.555-55", "charlie.davis@example.com", passwordEncoder.encode("password123"), "ADMIN"),
                new User(null, "David Wilson", "666.666.666-66", "david.wilson@example.com", passwordEncoder.encode("password123"), "USER"),
                new User(null, "Eva Harris", "777.777.777-77", "eva.harris@example.com", passwordEncoder.encode("password123"), "USER"),
                new User(null, "Frank Clark", "888.888.888-88", "frank.clark@example.com", passwordEncoder.encode("password123"), "USER"),
                new User(null, "Grace Lewis", "999.999.999-99", "grace.lewis@example.com", passwordEncoder.encode("password123"), "ADMIN"),
                new User(null, "Hank Walker", "000.000.000-00", "hank.walker@example.com", passwordEncoder.encode("password123"), "USER")
            );
            userRepository.saveAll(users);
        }
    }
}

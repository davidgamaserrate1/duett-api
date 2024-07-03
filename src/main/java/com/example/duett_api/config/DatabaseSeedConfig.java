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
                new User(null, "David", "111.111.111-11", "admin@admin", passwordEncoder.encode("admin"), "ADMIN"),
                new User(null, "Jane Smith", "222.222.222-22", "user@user", passwordEncoder.encode("user"), "USER"),
                new User(null, "Alice Johnson", "333.333.333-33", "alice.333@example.com", passwordEncoder.encode("password123"), "USER"),
                new User(null, "Bob Brown", "444.444.444-44", "bob.444@example.com", passwordEncoder.encode("password123"), "USER"),
                new User(null, "Charlie Davis", "555.555.555-55", "charlie.555@example.com", passwordEncoder.encode("password123"), "ADMIN"),
                new User(null, "David Wilson", "666.666.666-66", "david.666@example.com", passwordEncoder.encode("password123"), "USER"),
                new User(null, "Eva Harris", "777.777.777-77", "eva.777@example.com", passwordEncoder.encode("password123"), "USER"),
                new User(null, "Frank Clark", "888.888.888-88", "frank.888@example.com", passwordEncoder.encode("password123"), "USER"),
                new User(null, "Grace Lewis", "999.999.999-99", "grace.999@example.com", passwordEncoder.encode("password123"), "ADMIN"),
                new User(null, "Hank Walker", "000.000.000-00", "hank.000@example.com", passwordEncoder.encode("password123"), "USER"),
                new User(null, "Marcus", "222.000.000-00", "mark.wa11lker@example.com", passwordEncoder.encode("password123"), "USER"),
                new User(null, "Hank 22", "111.222.000-00", "walk33.er@example.com", passwordEncoder.encode("password123"), "USER"),
                new User(null, "Wilson Hank ", "333.222.000-00", "hank.charli333.222e@example.com", passwordEncoder.encode("password123"), "ADMIN"),
                new User(null, "Alice Wilson", "444.222.000-00", "hank.walke444.222r@example.com", passwordEncoder.encode("password123"), "USER"),
                new User(null, "Charlie Smith", "555.666.000-00", "hank.walk555.666er@example.com", passwordEncoder.encode("password123"), "ADMIN")
            );
            userRepository.saveAll(users);
        }
    }
}

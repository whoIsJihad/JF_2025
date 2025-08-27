package com.example.myapp;

import com.example.myapp.model.User;
import com.example.myapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyappApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyappApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserRepository userRepository) {
        return args -> {
            User user = new User("Johssn Doe", "joeeehn@example.com", "password123");
            userRepository.save(user);
            System.out.println("User saved: " + user.getId());
        };
    }
}

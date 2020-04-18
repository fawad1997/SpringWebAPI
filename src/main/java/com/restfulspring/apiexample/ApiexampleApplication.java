package com.restfulspring.apiexample;

import com.restfulspring.apiexample.entity.ApplicationUser;
import com.restfulspring.apiexample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ApiexampleApplication {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void seedUser() {
        if (userRepository.findByUsername("test") == null) {
            String encodedPassword = passwordEncoder.encode("12345");
            ApplicationUser user = new ApplicationUser(1, "test", encodedPassword);
            userRepository.save(user);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiexampleApplication.class, args);
    }

}

package com.restfulspring.apiexample;

import com.restfulspring.apiexample.entity.ApplicationUser;
import com.restfulspring.apiexample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ApiexampleApplication {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void seedUser() {
        if (userRepository.findByUsername("test") == null) {
            ApplicationUser user = new ApplicationUser(1, "test", "12345");
            userRepository.save(user);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiexampleApplication.class, args);
    }

}

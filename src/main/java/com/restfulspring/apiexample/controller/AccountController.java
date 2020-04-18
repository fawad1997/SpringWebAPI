package com.restfulspring.apiexample.controller;

import com.restfulspring.apiexample.entity.ApplicationUser;
import com.restfulspring.apiexample.service.JwtUtilService;
import com.restfulspring.apiexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtilService jwtUtil;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody ApplicationUser userCredentials) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Invalid Credentials");
        }
        return jwtUtil.generateToken(userCredentials.getUsername());
    }

    @PostMapping("/register")
    public String register(@RequestBody ApplicationUser user) {
        if (userService.registerUser(user)) {
            return "User Created";
        }
        return "User Creation Failed!";
    }
}

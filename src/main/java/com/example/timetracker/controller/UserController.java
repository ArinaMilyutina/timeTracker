package com.example.timetracker.controller;

import com.example.timetracker.dto.AuthDto;
import com.example.timetracker.dto.RegDto;
import com.example.timetracker.exception.NotFoundException;
import com.example.timetracker.jwt.JWTTokenProvider;
import com.example.timetracker.entity.User;
import com.example.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<User> registration(@RequestBody RegDto regDto) throws NotFoundException {
        return ResponseEntity.ok(userService.save(regDto));
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthDto dto) {
        User byUsername = userService.findByUsername(dto.getUsername());

        if (byUsername == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        if (passwordEncoder.matches(dto.getPassword(), byUsername.getPassword())) {
            String token = jwtTokenProvider.generateToken(dto.getUsername(), byUsername.getRoles());
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.badRequest().body("Invalid credentials");
    }
}




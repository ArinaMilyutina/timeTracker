package com.example.timetracker.controller;

import com.example.timetracker.dto.AuthDto;
import com.example.timetracker.dto.RegDto;
import com.example.timetracker.exception.EntityAlreadyExistsException;
import com.example.timetracker.jwt.JWTTokenProvider;
import com.example.timetracker.entity.user.User;
import com.example.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<User> registration(@RequestBody RegDto regDto) throws EntityAlreadyExistsException {
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

    @DeleteMapping("/current")
    public ResponseEntity<Void> deleteCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            boolean isDeleted = userService.deleteUserByUsername(username);
            return isDeleted ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/updateUser")
    public ResponseEntity<User> updateCurrentUser(@RequestBody User updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User updatedUserResponse = userService.updateUserByUsername(username, updatedUser);
            return ResponseEntity.ok(updatedUserResponse);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}




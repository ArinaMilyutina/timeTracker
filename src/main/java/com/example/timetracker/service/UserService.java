package com.example.timetracker.service;

import com.example.timetracker.dto.RegDto;
import com.example.timetracker.entity.Role;
import com.example.timetracker.entity.User;
import com.example.timetracker.exception.NotFoundException;
import com.example.timetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public User save(RegDto regDto) throws NotFoundException {
        if (userRepository.existsByUsername(regDto.getUsername())) {
            throw new NotFoundException("User already exists");
        }
        User user = User.builder().username(regDto.getUsername()).password(passwordEncoder().encode(regDto.getPassword())).name(regDto.getName()).roles(Set.of(Role.USER)).build();
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            User user = byUsername.get();
            return user;
        }
        throw new UsernameNotFoundException("Username not found!");
    }

}

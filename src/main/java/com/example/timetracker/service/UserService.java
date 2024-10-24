package com.example.timetracker.service;

import com.example.timetracker.dto.RegDto;
import com.example.timetracker.entity.user.Role;
import com.example.timetracker.entity.user.User;
import com.example.timetracker.exception.EntityAlreadyExistsException;
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
    private static final String USER_ALREADY_EXISTS = "User already exists";
    private static final String USER_NOT_FOUND = "User not found: ";
    private static final String USERNAME_NOT_FOUND = "Username not found";
    @Autowired
    private UserRepository userRepository;


    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public User save(RegDto regDto) throws EntityAlreadyExistsException {
        if (userRepository.existsByUsername(regDto.getUsername())) {
            throw new EntityAlreadyExistsException(USER_ALREADY_EXISTS);
        }
        User user = User.builder().username(regDto.getUsername()).password(passwordEncoder().encode(regDto.getPassword())).name(regDto.getName()).roles(Set.of(regDto.getRoles().toArray(new Role[0]))).build();
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + username));
    }

    public boolean deleteUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            User user = byUsername.get();
            return user;
        }
        throw new UsernameNotFoundException(USERNAME_NOT_FOUND);
    }


    public User updateUserByUsername(String username, User updatedUser) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(updatedUser.getUsername());
            user.setPassword(passwordEncoder().encode(updatedUser.getPassword()));
            user.setName(updatedUser.getName());
            userRepository.save(user);
            return user;

        }
        return null;
    }
}

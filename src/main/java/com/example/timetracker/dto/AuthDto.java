package com.example.timetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {
    @NotBlank(message = "Username is mandatory")
    @Size(max = 100, message = "Username must be less than 100 characters.")
    private String username;
    @NotBlank(message = "Password is mandatory")
    @Size(max = 100, message = "Password must be less than 100 characters.")
    private String password;
}

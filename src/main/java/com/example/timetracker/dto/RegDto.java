package com.example.timetracker.dto;

import com.example.timetracker.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegDto {
    @NotNull
    @NotBlank(message = "Username is mandatory")
    @Size(min = 1,max = 100, message = "Username must be less than 100 characters.")
    private String username;
    @NotNull
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 100, message = "Password must be at less than 100 characters.")
    private String password;
    @NotNull
    @NotBlank(message = "Name is mandatory")
    @Size(min = 1, max = 100, message = "Name must be at at less 100 characters.")
    private String name;
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}

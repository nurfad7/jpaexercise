package com.nurfad.jpaexercise.infrastructure.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDTO {
    @NotBlank
    @Size(max = 50)
    private String name;

    @Email
    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;
}

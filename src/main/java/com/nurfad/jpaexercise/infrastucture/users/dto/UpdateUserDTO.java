package com.nurfad.jpaexercise.infrastucture.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    @NotBlank
    private Long userId;

    @Size(max = 50)
    private String name;

    @Email
    @NotBlank
    @Size(max = 50)
    private String email;

    @Size(max = 100)
    private String profilePictureUrl;

    private String quotes;
}

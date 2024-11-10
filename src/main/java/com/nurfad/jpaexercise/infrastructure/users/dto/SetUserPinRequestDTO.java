package com.nurfad.jpaexercise.infrastructure.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetUserPinRequestDTO {
    @NotBlank
    private Long id;

    @NotBlank
    @Size(min = 4, max = 4)
    private String pin;
}

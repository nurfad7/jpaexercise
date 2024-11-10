package com.nurfad.jpaexercise.usecase.auth;

import com.nurfad.jpaexercise.infrastructure.users.dto.LoginRequestDTO;
import com.nurfad.jpaexercise.infrastructure.users.dto.LoginResponseDTO;

public interface LoginUseCase {
    LoginResponseDTO login(LoginRequestDTO req);
}

package com.nurfad.jpaexercise.usecase.auth;

import com.nurfad.jpaexercise.infrastucture.users.dto.LoginRequestDTO;
import com.nurfad.jpaexercise.infrastucture.users.dto.LoginResponseDTO;

public interface LoginUseCase {
    LoginResponseDTO login(LoginRequestDTO req);
}

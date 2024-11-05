package com.nurfad.jpaexercise.usecase.auth;

import com.nurfad.jpaexercise.infrastucture.users.dto.LoginResponseDTO;

public interface LogoutUseCase {
    void logout(LoginResponseDTO req);
}

package com.nurfad.jpaexercise.usecase.auth;

import com.nurfad.jpaexercise.infrastructure.users.dto.SignupRequestDTO;

public interface SignupUseCase {
    void signUp(SignupRequestDTO req);
}

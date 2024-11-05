package com.nurfad.jpaexercise.usecase.auth;

import com.nurfad.jpaexercise.infrastucture.users.dto.SignupRequestDTO;

public interface SignupUseCase {
    void signUp(SignupRequestDTO req);
}

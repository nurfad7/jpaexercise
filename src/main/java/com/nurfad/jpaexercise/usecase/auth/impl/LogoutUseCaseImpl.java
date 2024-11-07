package com.nurfad.jpaexercise.usecase.auth.impl;

import com.nurfad.jpaexercise.infrastucture.users.dto.LoginResponseDTO;
import com.nurfad.jpaexercise.usecase.auth.LogoutUseCase;
import org.springframework.stereotype.Service;

@Service
public class LogoutUseCaseImpl implements LogoutUseCase {
    @Override
    public void logout(LoginResponseDTO req) {
        req.getToken();
        //token destroy not implemented yet
    }
}

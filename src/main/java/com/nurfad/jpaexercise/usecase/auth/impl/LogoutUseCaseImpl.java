package com.nurfad.jpaexercise.usecase.auth.impl;

import com.nurfad.jpaexercise.entity.User;
import com.nurfad.jpaexercise.infrastucture.users.dto.LoginResponseDTO;
import com.nurfad.jpaexercise.usecase.auth.LogoutUseCase;
import com.nurfad.jpaexercise.infrastucture.users.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LogoutUseCaseImpl implements LogoutUseCase {
    private final UsersRepository usersRepository;

    public LogoutUseCaseImpl(final UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void logout(LoginResponseDTO req) {
        Optional<User> currentUser = usersRepository.findById(req.getId());
        //token destroy not implemented yet
    }
}

package com.nurfad.jpaexercise.usecase.user.impl;

import com.nurfad.jpaexercise.common.exceptions.DataNotFoundException;
import com.nurfad.jpaexercise.entity.User;
import com.nurfad.jpaexercise.infrastructure.users.repository.UsersRepository;
import com.nurfad.jpaexercise.usecase.user.GetUserUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetUserUseCaseImpl implements GetUserUseCase {
    private final UsersRepository usersRepository;

    public GetUserUseCaseImpl(final UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> existingUser = usersRepository.findById(id);
        if(existingUser.isEmpty()) {
            throw new DataNotFoundException("There is no user with ID: " + id);
        }
        return existingUser.get();
    }
}

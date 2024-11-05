package com.nurfad.jpaexercise.usecase.user.impl;

import com.nurfad.jpaexercise.common.exceptions.DataNotFoundException;
import com.nurfad.jpaexercise.entity.User;
import com.nurfad.jpaexercise.infrastucture.users.dto.SetUserPinRequestDTO;
import com.nurfad.jpaexercise.infrastucture.users.repository.UsersRepository;
import com.nurfad.jpaexercise.usecase.user.SetUserPinUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SetUserPinUseCaseImpl implements SetUserPinUseCase {
    private final UsersRepository usersRepository;

    public SetUserPinUseCaseImpl(final UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void setPin(SetUserPinRequestDTO req) {
        Optional<User> existingUser = usersRepository.findById(req.getId());
        if(existingUser.isEmpty()) {
            throw new DataNotFoundException("There is no user with ID: " + req.getId());
        }
        User userToChange = existingUser.get();
        userToChange.setPin(req.getPin());
        usersRepository.save(userToChange);
    }
}

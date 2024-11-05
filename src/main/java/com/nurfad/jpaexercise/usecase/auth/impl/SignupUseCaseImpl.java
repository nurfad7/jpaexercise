package com.nurfad.jpaexercise.usecase.auth.impl;

import com.nurfad.jpaexercise.common.exceptions.DuplicateUniqueDataException;
import com.nurfad.jpaexercise.entity.User;
import com.nurfad.jpaexercise.entity.UserDetail;
import com.nurfad.jpaexercise.infrastucture.users.dto.SignupRequestDTO;
import com.nurfad.jpaexercise.infrastucture.users.repository.UserDetailsRepository;
import com.nurfad.jpaexercise.infrastucture.users.repository.UsersRepository;
import com.nurfad.jpaexercise.usecase.auth.SignupUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SignupUseCaseImpl implements SignupUseCase {
    private final UsersRepository usersRepository;
    private final UserDetailsRepository userDetailsRepository;

    public SignupUseCaseImpl(final UsersRepository usersRepository,
                             final UserDetailsRepository userDetailsRepository) {
        this.usersRepository = usersRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    @Transactional
    public void signUp(SignupRequestDTO req) {
        Optional<User> existingUser = usersRepository.findByEmail(req.getEmail());
        if(existingUser.isPresent()) {
            throw new DuplicateUniqueDataException("Email already exist: " + req.getEmail());
        }
        User newUser = new User();
        newUser.setEmail(req.getEmail());
        newUser.setPassword(req.getPassword());
        newUser = usersRepository.save(newUser);
        UserDetail newUserDetail = new UserDetail();
        newUserDetail.setUser(newUser);
        newUserDetail.setName(req.getName());
        userDetailsRepository.save(newUserDetail);
    }
}

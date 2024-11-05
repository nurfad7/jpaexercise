package com.nurfad.jpaexercise.usecase.auth.impl;

import com.nurfad.jpaexercise.common.exceptions.DataNotFoundException;
import com.nurfad.jpaexercise.entity.User;
import com.nurfad.jpaexercise.entity.UserDetail;
import com.nurfad.jpaexercise.infrastucture.users.dto.LoginRequestDTO;
import com.nurfad.jpaexercise.infrastucture.users.dto.LoginResponseDTO;
import com.nurfad.jpaexercise.infrastucture.users.repository.UserDetailsRepository;
import com.nurfad.jpaexercise.infrastucture.users.repository.UsersRepository;
import com.nurfad.jpaexercise.usecase.auth.LoginUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LoginUseCaseImpl implements LoginUseCase {
    private final UsersRepository usersRepository;
    private final UserDetailsRepository userDetailsRepository;

    public LoginUseCaseImpl(final UsersRepository usersRepository,
                            final UserDetailsRepository userDetailsRepository) {
        this.usersRepository = usersRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    @Transactional
    public LoginResponseDTO login(LoginRequestDTO req) {
        Optional<User> currentUser = usersRepository.findByEmailAndPassword(req.getEmail(),
                req.getPassword());
        if(currentUser.isEmpty()) {
            throw new DataNotFoundException("Wrong credentials!");
        }
        User user = currentUser.get();
        Optional<UserDetail> currentUserDetail = userDetailsRepository.findByUserId(user.getId());
        if(currentUserDetail.isEmpty()) {
            throw new DataNotFoundException("User detail not found");
        }
        return new LoginResponseDTO(
                user.getId(),
                currentUserDetail.get().getName(),
                "Dummy Token"
        );
    }
}

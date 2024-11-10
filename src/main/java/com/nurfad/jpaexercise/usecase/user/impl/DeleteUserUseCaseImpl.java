package com.nurfad.jpaexercise.usecase.user.impl;

import com.nurfad.jpaexercise.common.exceptions.DataNotFoundException;
import com.nurfad.jpaexercise.entity.User;
import com.nurfad.jpaexercise.entity.UserDetail;
import com.nurfad.jpaexercise.infrastructure.users.repository.UserDetailsRepository;
import com.nurfad.jpaexercise.infrastructure.users.repository.UsersRepository;
import com.nurfad.jpaexercise.usecase.user.DeleteUserUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private final UsersRepository usersRepository;
    private final UserDetailsRepository userDetailsRepository;

    public DeleteUserUseCaseImpl(final UsersRepository usersRepository,
                             final UserDetailsRepository userDetailsRepository) {
        this.usersRepository = usersRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    @Transactional
    public void delete(User user) {
        usersRepository.delete(user);
        Optional<UserDetail> currentUserDetail = userDetailsRepository.findByUserId(user.getId());
        if(currentUserDetail.isEmpty()) {
            throw new DataNotFoundException("User detail not found");
        }
        userDetailsRepository.delete(currentUserDetail.get());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        usersRepository.deleteById(id);
        Optional<UserDetail> currentUserDetail = userDetailsRepository.findByUserId(id);
        if(currentUserDetail.isEmpty()) {
            throw new DataNotFoundException("User detail not found");
        }
        userDetailsRepository.delete(currentUserDetail.get());
    }
}

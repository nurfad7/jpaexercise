package com.nurfad.jpaexercise.usecase.user.impl;

import com.nurfad.jpaexercise.common.exceptions.DataNotFoundException;
import com.nurfad.jpaexercise.entity.User;
import com.nurfad.jpaexercise.entity.UserDetail;
import com.nurfad.jpaexercise.infrastucture.users.dto.UpdateUserDTO;
import com.nurfad.jpaexercise.infrastucture.users.repository.UserDetailsRepository;
import com.nurfad.jpaexercise.infrastucture.users.repository.UsersRepository;
import com.nurfad.jpaexercise.usecase.user.UpdateUserUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UsersRepository usersRepository;
    private final UserDetailsRepository userDetailsRepository;

    public UpdateUserUseCaseImpl(final UsersRepository usersRepository,
                                 final UserDetailsRepository userDetailsRepository) {
        this.usersRepository = usersRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    @Transactional
    public UpdateUserDTO updateUserProfile(UpdateUserDTO req) {
        Optional<User> currentUser = usersRepository.findById(req.getUserId());
        if(currentUser.isEmpty()) {
            throw new DataNotFoundException("There is no user with ID: " + req.getUserId());
        }
        User user = currentUser.get();
        user.setEmail(req.getEmail());
        user = usersRepository.save(user);
        Optional<UserDetail> currentUserDetail = userDetailsRepository.findByUserId(user.getId());
        if(currentUserDetail.isEmpty()) {
            throw new DataNotFoundException("User detail not found");
        }
        UserDetail userDetail = currentUserDetail.get();
        userDetail.setName(req.getName());
        userDetail.setProfilePictureUrl(req.getProfilePictureUrl());
        userDetail.setQuotes(req.getQuotes());
        userDetail = userDetailsRepository.save(userDetail);
        return new UpdateUserDTO(
                user.getId(),
                userDetail.getName(),
                user.getEmail(),
                userDetail.getQuotes(),
                userDetail.getProfilePictureUrl()
        );
    }
}

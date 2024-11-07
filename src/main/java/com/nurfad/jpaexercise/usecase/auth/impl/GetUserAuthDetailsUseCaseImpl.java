package com.nurfad.jpaexercise.usecase.auth.impl;

import com.nurfad.jpaexercise.common.exceptions.DataNotFoundException;
import com.nurfad.jpaexercise.entity.User;
import com.nurfad.jpaexercise.infrastucture.users.dto.UserAuth;
import com.nurfad.jpaexercise.infrastucture.users.repository.UsersRepository;
import com.nurfad.jpaexercise.usecase.auth.GetUserAuthDetailsUseCase;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetUserAuthDetailsUseCaseImpl implements GetUserAuthDetailsUseCase {
    private final UsersRepository usersRepository;

    public GetUserAuthDetailsUseCaseImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User existingUser = usersRepository.findByEmailContainsIgnoreCase(username).orElseThrow(() -> new DataNotFoundException("User not found with email: " + username));

        UserAuth userAuth = new UserAuth();
        userAuth.setUser(existingUser);
        return userAuth;
    }
}

package com.nurfad.jpaexercise.usecase.auth.impl;

import com.nurfad.jpaexercise.common.exceptions.DuplicateUniqueDataException;
import com.nurfad.jpaexercise.entity.Role;
import com.nurfad.jpaexercise.entity.User;
import com.nurfad.jpaexercise.entity.UserDetail;
import com.nurfad.jpaexercise.infrastructure.users.dto.SignupRequestDTO;
import com.nurfad.jpaexercise.infrastructure.users.repository.RolesRepository;
import com.nurfad.jpaexercise.infrastructure.users.repository.UserDetailsRepository;
import com.nurfad.jpaexercise.infrastructure.users.repository.UsersRepository;
import com.nurfad.jpaexercise.usecase.auth.SignupUseCase;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SignupUseCaseImpl implements SignupUseCase {
    private final UsersRepository usersRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;

    public SignupUseCaseImpl(final UsersRepository usersRepository,
                             final UserDetailsRepository userDetailsRepository,
                             final PasswordEncoder passwordEncoder,
                             final RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
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
        newUser.setPassword(passwordEncoder.encode(req.getPassword()));
        Optional<Role> defaultRole = rolesRepository.findByName("USER");
        if (defaultRole.isPresent()) {
            newUser.getRoles().add(defaultRole.get());
        } else {
            throw new RuntimeException("Default role not found");
        }
        newUser = usersRepository.save(newUser);
        UserDetail newUserDetail = new UserDetail();
        newUserDetail.setUser(newUser);
        newUserDetail.setName(req.getName());
        userDetailsRepository.save(newUserDetail);
    }
}

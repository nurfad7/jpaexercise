package com.nurfad.jpaexercise.usecase.auth.impl;

import com.nurfad.jpaexercise.common.exceptions.DataNotFoundException;
import com.nurfad.jpaexercise.infrastructure.security.TokenService;
import com.nurfad.jpaexercise.infrastructure.users.dto.LoginRequestDTO;
import com.nurfad.jpaexercise.infrastructure.users.dto.LoginResponseDTO;
import com.nurfad.jpaexercise.usecase.auth.LoginUseCase;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log
@Service
public class LoginUseCaseImpl implements LoginUseCase {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginUseCaseImpl(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional
    public LoginResponseDTO login(LoginRequestDTO req) {
        try {
            log.info("Logging in with");
            log.info(req.getEmail());
            log.info(req.getPassword());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );
            String token = tokenService.generateToken(authentication);
            return new LoginResponseDTO(token);
        } catch (AuthenticationException e) {
            throw new DataNotFoundException("Wrong credentials");
        }
    }
}

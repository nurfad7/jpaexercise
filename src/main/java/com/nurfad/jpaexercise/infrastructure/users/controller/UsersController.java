package com.nurfad.jpaexercise.infrastructure.users.controller;

import com.nurfad.jpaexercise.common.responses.Response;
import com.nurfad.jpaexercise.infrastructure.users.dto.*;
import com.nurfad.jpaexercise.usecase.user.*;
import com.nurfad.jpaexercise.usecase.auth.*;
import com.nurfad.jpaexercise.infrastructure.security.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    private final SignupUseCase signupUseCase;
    private final LoginUseCase loginUseCase;
    private final TokenBlacklistUseCase tokenBlacklistUseCase;
    private final SetUserPinUseCase setUserPinUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final GetUserUseCase getUserUseCase;

    public UsersController(final SignupUseCase signupUseCase,
                           final LoginUseCase loginUseCase,
                           final TokenBlacklistUseCase tokenBlacklistUseCase,
                           final SetUserPinUseCase setUserPinUseCase,
                           final UpdateUserUseCase updateUserUseCase,
                           final GetUserUseCase getUserUseCase) {
        this.signupUseCase = signupUseCase;
        this.loginUseCase = loginUseCase;
        this.tokenBlacklistUseCase = tokenBlacklistUseCase;
        this.setUserPinUseCase = setUserPinUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.getUserUseCase = getUserUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return Response.successfulResponse(HttpStatus.CREATED.value(),
                "User info retrieved!",
                getUserUseCase.getUserById(id));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignupRequestDTO req) {
        signupUseCase.signUp(req);
        return Response.successfulResponse(HttpStatus.CREATED.value(),
                "You've been successfully signed up!", null);
    }

    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody LoginRequestDTO req) {
        return Response.successfulResponse("You've been successfully logged in!",
                loginUseCase.login(req));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logOut() {
        String token = Claims.getJwtTokenString();
        String expiredAt = Claims.getJwtExpirationDate();
        tokenBlacklistUseCase.blacklistToken(token, expiredAt);
        return Response.successfulResponse("You've been successfully logged out!", null);
    }

    @PostMapping("/pin")
    public ResponseEntity<?> setPin(@RequestBody SetUserPinRequestDTO req) {
        setUserPinUseCase.setPin(req);
        return Response.successfulResponse("Your pin successfully set!");
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateUserDTO req) {
        return Response.successfulResponse("Profile updated!",
                updateUserUseCase.updateUserProfile(req));
    }
}

package com.nurfad.jpaexercise.infrastucture.users.controller;

import com.nurfad.jpaexercise.common.responses.Response;
import com.nurfad.jpaexercise.infrastucture.users.dto.*;
import com.nurfad.jpaexercise.usecase.user.*;
import com.nurfad.jpaexercise.usecase.auth.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UsersPublicController {
    private final SignupUseCase signupUseCase;
    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;
    private final SetUserPinUseCase setUserPinUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    public UsersPublicController(final SignupUseCase signupUseCase,
                                 final LoginUseCase loginUseCase,
                                 final LogoutUseCase logoutUseCase,
                                 final SetUserPinUseCase setUserPinUseCase,
                                 final UpdateUserUseCase updateUserUseCase) {
        this.signupUseCase = signupUseCase;
        this.loginUseCase = loginUseCase;
        this.logoutUseCase = logoutUseCase;
        this.setUserPinUseCase = setUserPinUseCase;
        this.updateUserUseCase = updateUserUseCase;
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
    public ResponseEntity<?> logOut(@RequestBody LoginResponseDTO req) {
        logoutUseCase.logout(req);
        return Response.successfulResponse("You've been successfully logged out!");
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
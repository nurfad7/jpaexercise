package com.nurfad.jpaexercise.usecase.user;

import com.nurfad.jpaexercise.entity.User;

import java.util.List;

public interface GetUserUseCase {
    User getUserById(Long id);
}

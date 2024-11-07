package com.nurfad.jpaexercise.usecase.user;

import com.nurfad.jpaexercise.entity.User;

public interface GetUserUseCase {
    User getUserById(Long id);
}

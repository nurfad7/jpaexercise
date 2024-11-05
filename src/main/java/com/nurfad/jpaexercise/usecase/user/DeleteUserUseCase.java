package com.nurfad.jpaexercise.usecase.user;

import com.nurfad.jpaexercise.entity.User;

public interface DeleteUserUseCase {
    void delete(User user);
    void deleteById(Long id);
}

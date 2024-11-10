package com.nurfad.jpaexercise.usecase.user;

import com.nurfad.jpaexercise.infrastructure.users.dto.UpdateUserDTO;

public interface UpdateUserUseCase {
    UpdateUserDTO updateUserProfile(UpdateUserDTO req);
}

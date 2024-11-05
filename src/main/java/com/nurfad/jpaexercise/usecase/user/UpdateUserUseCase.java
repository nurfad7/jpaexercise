package com.nurfad.jpaexercise.usecase.user;

import com.nurfad.jpaexercise.infrastucture.users.dto.UpdateUserDTO;

public interface UpdateUserUseCase {
    UpdateUserDTO updateUserProfile(UpdateUserDTO req);
}

package com.nurfad.jpaexercise.usecase.user;

import com.nurfad.jpaexercise.infrastructure.users.dto.SetUserPinRequestDTO;

public interface SetUserPinUseCase {
    void setPin(SetUserPinRequestDTO req);
}

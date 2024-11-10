package com.nurfad.jpaexercise.usecase.auth;

public interface TokenBlacklistUseCase {
    void blacklistToken(String token, String expiredAt);
    boolean isTokenBlacklisted(String token);
}

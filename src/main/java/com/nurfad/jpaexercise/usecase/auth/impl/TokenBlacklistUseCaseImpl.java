package com.nurfad.jpaexercise.usecase.auth.impl;

import com.nurfad.jpaexercise.infrastructure.repository.RedisTokenRepository;
import com.nurfad.jpaexercise.usecase.auth.TokenBlacklistUseCase;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class TokenBlacklistUseCaseImpl implements TokenBlacklistUseCase {
    private final RedisTokenRepository redisTokenRepository;

    public TokenBlacklistUseCaseImpl(RedisTokenRepository redisTokenRepository) {
        this.redisTokenRepository = redisTokenRepository;
    }

    @Override
    public void blacklistToken(String token, String expiredAt) {
        Duration duration = Duration.between(java.time.Instant.now(), java.time.Instant.parse(expiredAt));
        redisTokenRepository.saveToken(token, duration);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return redisTokenRepository.isTokenBlacklisted(token);
    }
}

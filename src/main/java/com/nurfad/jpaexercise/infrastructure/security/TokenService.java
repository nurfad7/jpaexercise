package com.nurfad.jpaexercise.infrastructure.security;

import com.nurfad.jpaexercise.common.exceptions.DataNotFoundException;
import com.nurfad.jpaexercise.entity.User;
import com.nurfad.jpaexercise.infrastructure.users.repository.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {
  private final JwtEncoder jwtEncoder;
  private final UsersRepository usersRepository;

  public TokenService(JwtEncoder jwtEncoder, UsersRepository usersRepository) {
    this.jwtEncoder = jwtEncoder;
    this.usersRepository = usersRepository;
  }

  public String generateToken(Authentication authentication) {
    Instant now = Instant.now();
    long expiry = 36000L;

    String email = authentication.getName();

    User user = usersRepository.findByEmailContainsIgnoreCase(email).orElseThrow(() -> new DataNotFoundException("User not found"));

    String scope = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .reduce((a, b) -> a + " " + b)
            .orElse("");

    JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiry))
            .subject(email)
            .claim("scope", scope)
            .claim("userId", user.getId())
            .build();

    return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}
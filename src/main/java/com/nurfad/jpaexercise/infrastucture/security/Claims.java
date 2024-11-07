package com.nurfad.jpaexercise.infrastucture.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import lombok.extern.java.Log;

import java.util.Collection;
import java.util.Map;

@Log
public class Claims {

  public static Map<String, Object> getClaimsFromJwt() {
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication authentication = context.getAuthentication();

    if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
      throw new IllegalStateException("JWT not found in SecurityContext");
    }

    log.info(authentication.toString());

    return jwt.getClaims();
  }

  public static String getEmailFromJwt() {
    return (String) getClaimsFromJwt().get("sub");
  }

  public static String getRoleFromJwt() {
    Object roles = getClaimsFromJwt().get("roles");
    if (roles instanceof String) {
      return (String) roles;
    } else if (roles instanceof Collection) {
      return ((Collection<?>) roles).stream()
              .map(Object::toString)
              .findFirst()
              .orElse(null);
    }
    return null;
  }

  public static Long getUserIdFromJwt() {
    Object userId = getClaimsFromJwt().get("userId");
    if (userId instanceof Integer) {
      return ((Integer) userId).longValue();
    } else if (userId instanceof Long) {
      return (Long) userId;
    } else if (userId instanceof String) {
      return Long.parseLong((String) userId);
    }
    throw new IllegalStateException("User ID not found in JWT");
  }
}
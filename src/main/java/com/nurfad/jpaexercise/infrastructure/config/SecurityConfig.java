package com.nurfad.jpaexercise.infrastructure.config;

import com.nurfad.jpaexercise.infrastructure.security.filters.TokenBlacklist;
import com.nurfad.jpaexercise.usecase.auth.GetUserAuthDetailsUseCase;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.Cookie;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Log
public class SecurityConfig {
  private final GetUserAuthDetailsUseCase getUserAuthDetailsUseCase;
  private final PasswordEncoder passwordEncoder;
  private final RsaKeyConfigProperties rsaKeyConfigProperties;
  private final TokenBlacklist tokenBlacklistFilter;

  public SecurityConfig(GetUserAuthDetailsUseCase getUserAuthDetailsUseCase,
                        PasswordEncoder passwordEncoder,
                        RsaKeyConfigProperties rsaKeyConfigProperties,
                        TokenBlacklist tokenBlacklist) {
    this.getUserAuthDetailsUseCase = getUserAuthDetailsUseCase;
    this.passwordEncoder = passwordEncoder;
    this.rsaKeyConfigProperties = rsaKeyConfigProperties;
    this.tokenBlacklistFilter = tokenBlacklist;
  }

  @Bean
  public AuthenticationManager authManager() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(getUserAuthDetailsUseCase);
    authProvider.setPasswordEncoder(passwordEncoder);
    return new ProviderManager(authProvider);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/error/**").permitAll()
                    .requestMatchers("/api/v1/users/login").permitAll()
                    .requestMatchers("/api/v1/users/sign-up").permitAll()
                    .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2ResourceServer(oauth2 -> {
              oauth2.jwt(jwt -> jwt.decoder(jwtDecoder()));
              oauth2.bearerTokenResolver(request -> {
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                  for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("SID")) {
                      return cookie.getValue();
                    }
                  }
                }

                // Get from headers instead of cookies
                var header = request.getHeader("Authorization");
                if (header != null) {
                  return header.replace("Bearer ", "");
                }

                return null;
              });
            })
            .addFilterAfter(tokenBlacklistFilter, BearerTokenAuthenticationFilter.class)
            .userDetailsService(getUserAuthDetailsUseCase)
            .build();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(rsaKeyConfigProperties.publicKey()).build();
  }

  @Bean
  JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(rsaKeyConfigProperties.publicKey()).privateKey(rsaKeyConfigProperties.privateKey()).build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
  }
}
package com.rishirajdhr.flagship.auth.jwt;

import java.util.Date;
import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Provides utilities to work with JSON Web Tokens (JWTs) for authorization.
 */
@Service
public class JWTService {
  private final SecretKey secretKey = Jwts.SIG.HS256.key().build();
  private final long VALID_DURATION_MS = 3600_000; // 1 hour

  /**
   * Generate a JWT for an application user.
   *
   * @param userDetails the user details of the application user
   * @return the generated JWT
   */
  public String generateToken(UserDetails userDetails) {
    return Jwts.builder()
        .subject(userDetails.getUsername())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + VALID_DURATION_MS))
        .signWith(secretKey)
        .compact();
  }

  /**
   * Extract the username encoded in a JWT.
   *
   * @param token the JWT containing the username
   * @return the username if found, {@code null} otherwise
   */
  public String extractUsername(String token) {
    return parseToken(token).getSubject();
  }

  /**
   * Validate a JWT for an application user.
   *
   * @param token the token to validate
   * @param userDetails the user details of the application user
   * @return {@code true} if the JWT is valid for the given user, {@code false} otherwise
   */
  public boolean validateToken(String token, UserDetails userDetails) {
    Claims claims = parseToken(token);
    boolean isUserValid = claims.getSubject().equals(userDetails.getUsername());
    boolean isExpired = claims.getExpiration().before(new Date());
    return isUserValid && !isExpired;
  }

  /**
   * Parse a JWT and extract the encoded payload.
   *
   * @param token the token to parse
   * @return the decoded JWT payload
   */
  private Claims parseToken(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
  }
}

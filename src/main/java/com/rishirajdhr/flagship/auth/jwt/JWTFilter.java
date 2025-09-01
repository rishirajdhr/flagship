package com.rishirajdhr.flagship.auth.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Represents a Spring Security filter that intercepts incoming HTTP requests and attempts to
 * authenticate them based on a JSON Web Token (JWT) provided in the {@code Authorization} header.
 */
@Component
public class JWTFilter extends OncePerRequestFilter {
  private final JWTService jwtService;
  private final UserDetailsService userDetailsService;

  /**
   * Create a filter that attempts to authenticate incoming HTTP requests based on a JWT.
   *
   * @param jwtService the service that provides utilities to work with JWTs
   * @param userDetailsService the service that provides the required user information
   */
  public JWTFilter(JWTService jwtService, UserDetailsService userDetailsService) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String AUTHORIZATION_HEADER_KEY = "Authorization";
    String AUTHORIZATION_HEADER_VALUE_PREFIX = "Bearer ";

    String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER_KEY);
    if (authorizationHeader == null || !authorizationHeader.startsWith(AUTHORIZATION_HEADER_VALUE_PREFIX)) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = authorizationHeader.substring(AUTHORIZATION_HEADER_VALUE_PREFIX.length());
    String username = jwtService.extractUsername(token);
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      boolean isTokenValid = jwtService.validateToken(token, userDetails);
      if (isTokenValid) {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }

    filterChain.doFilter(request, response);
  }
}

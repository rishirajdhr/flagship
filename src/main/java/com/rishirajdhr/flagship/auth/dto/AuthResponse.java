package com.rishirajdhr.flagship.auth.dto;

/**
 * Represents the response returned after successful authentication.
 *
 * @param token the JWT generated for the user
 */
public record AuthResponse(String token) {}

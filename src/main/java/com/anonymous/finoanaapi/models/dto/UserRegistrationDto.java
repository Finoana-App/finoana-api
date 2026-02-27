package com.anonymous.finoanaapi.models.dto;

import java.util.Optional;

public record UserRegistrationDto(
    String firebaseToken,
    String email,
    String firstName,
    String lastName,
    String displayName,
    Optional<String> photoUrl,
    Optional<String> bio) {}

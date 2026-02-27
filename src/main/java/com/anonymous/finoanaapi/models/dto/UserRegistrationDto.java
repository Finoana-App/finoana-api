package com.anonymous.finoanaapi.models.dto;

import java.util.Optional;

public record UserRegistrationDto(
    String id, String email, String displayName, Optional<String> photoUrl, Optional<String> bio) {}

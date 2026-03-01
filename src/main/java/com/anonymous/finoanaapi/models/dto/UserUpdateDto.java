package com.anonymous.finoanaapi.models.dto;

import com.anonymous.finoanaapi.models.enums.PrivacyLevel;
import java.util.Optional;

public record UserUpdateDto(
    Optional<String> displayName,
    Optional<String> photoUrl,
    Optional<String> bio,
    Optional<PrivacyLevel> privacyLevel) {}

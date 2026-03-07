package com.anonymous.finoanaapi.models.dto;

import com.anonymous.finoanaapi.models.enums.PostVisibility;

public record CreatePostDto(
    String userId, String content, boolean isAnonymous, PostVisibility visibility) {}

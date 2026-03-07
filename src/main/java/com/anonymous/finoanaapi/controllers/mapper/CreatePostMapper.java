package com.anonymous.finoanaapi.controllers.mapper;

import com.anonymous.finoanaapi.controllers.model.CreatePost;
import com.anonymous.finoanaapi.models.dto.CreatePostDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreatePostMapper extends AbstractDomaineToRestMapper<CreatePostDto, CreatePost> {
  private final PostVisibilityMapper postVisibilityMapper;

  @Override
  public CreatePostDto toDomain(CreatePost rest) {
    return new CreatePostDto(
        rest.getAuthorId(),
        rest.getContent(),
        rest.getAnonymous(),
        postVisibilityMapper.toDomain(rest.getVisibility()));
  }
}

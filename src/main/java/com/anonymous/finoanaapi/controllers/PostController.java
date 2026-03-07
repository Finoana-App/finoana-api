package com.anonymous.finoanaapi.controllers;

import com.anonymous.finoanaapi.controllers.mapper.CreatePostMapper;
import com.anonymous.finoanaapi.controllers.mapper.PostToBasePostMapper;
import com.anonymous.finoanaapi.controllers.model.BasePost;
import com.anonymous.finoanaapi.controllers.model.CreatePost;
import com.anonymous.finoanaapi.controllers.validator.CurrentUserAuthorityPostCreationValidator;
import com.anonymous.finoanaapi.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {
  private final PostService postService;
  private final CreatePostMapper createPostMapper;
  private final PostToBasePostMapper postToBasePostMapper;
  private final CurrentUserAuthorityPostCreationValidator currentUserAuthorityPostCreationValidator;

  @PostMapping
  BasePost createPost(@RequestBody CreatePost post) {
    currentUserAuthorityPostCreationValidator.accept(post);

    return postToBasePostMapper.toRest(postService.save(createPostMapper.toDomain(post)));
  }
}

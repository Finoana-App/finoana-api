package com.anonymous.finoanaapi.services;

import com.anonymous.finoanaapi.models.Post;
import com.anonymous.finoanaapi.models.User;
import com.anonymous.finoanaapi.models.dto.CreatePostDto;
import com.anonymous.finoanaapi.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
  private PostRepository postRepository;

  public Post save(CreatePostDto toSave) {
    return postRepository.save(
        Post.builder()
            .author(User.builder().id(toSave.userId()).build())
            .content(toSave.content())
            .visibility(toSave.visibility())
            .anonymous(toSave.isAnonymous())
            .build());
  }
}

package com.anonymous.finoanaapi.services;

import com.anonymous.finoanaapi.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
  private PostRepository postRepository;
}

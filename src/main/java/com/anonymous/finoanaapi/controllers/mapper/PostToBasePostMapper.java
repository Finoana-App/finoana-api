package com.anonymous.finoanaapi.controllers.mapper;

import com.anonymous.finoanaapi.controllers.model.BasePost;
import com.anonymous.finoanaapi.models.Post;
import org.springframework.stereotype.Component;

@Component
public class PostToBasePostMapper extends AbstractDomaineToRestMapper<Post, BasePost> {
  @Override
  public BasePost toRest(Post domain) {
    return new BasePost().id(domain.getId()).content(domain.getContent());
  }
}

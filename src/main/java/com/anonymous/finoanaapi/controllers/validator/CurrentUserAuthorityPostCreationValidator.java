package com.anonymous.finoanaapi.controllers.validator;

import static com.anonymous.finoanaapi.models.enums.UserRole.USER;
import static com.anonymous.finoanaapi.utils.security.firebase.FirebaseFilter.getPrincipal;

import com.anonymous.finoanaapi.controllers.exceptions.ForbiddenException;
import com.anonymous.finoanaapi.controllers.model.CreatePost;
import java.util.function.Consumer;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserAuthorityPostCreationValidator implements Consumer<CreatePost> {
  @Override
  public void accept(CreatePost createPost) {
    var principal = getPrincipal();

    if (USER.equals(principal.getRole())) {
      var currentUserId = principal.getId();
      var postAuthorId = createPost.getAuthorId();
      if (!currentUserId.equals(postAuthorId)) {
        throw new ForbiddenException(
            "User with id %s not allow to create post for the user %s"
                .formatted(currentUserId, postAuthorId));
      }
    }
  }
}

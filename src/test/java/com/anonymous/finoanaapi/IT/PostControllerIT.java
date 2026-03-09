package com.anonymous.finoanaapi.IT;

import static com.anonymous.finoanaapi.config.HttpExceptionAssertion.assertThrowsForbiddenException;
import static com.anonymous.finoanaapi.controllers.model.PostVisibility.PUBLIC;
import static com.anonymous.finoanaapi.utils.DummyToken.someToken;
import static com.anonymous.finoanaapi.utils.DummyUser.someUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.anonymous.finoanaapi.config.TestConfig;
import com.anonymous.finoanaapi.config.UserSetup;
import com.anonymous.finoanaapi.controllers.api.PostsApi;
import com.anonymous.finoanaapi.controllers.client.ApiException;
import com.anonymous.finoanaapi.controllers.model.CreatePost;
import com.google.firebase.auth.FirebaseAuthException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PostControllerIT extends TestConfig {
  @Autowired private UserSetup userSetup;

  @BeforeEach
  void setUp() throws FirebaseAuthException {
    userSetup.setup(userRegistration);
  }

  @AfterEach
  void shutdown() {
    userSetup.shutdown(userRegistration);
  }

  @Test
  void user_create_own_post_ok() throws ApiException {
    var postsApi = new PostsApi(anApiClient(userSetup.getToken()));
    var toBeCreated =
        new CreatePost()
            .content("Hi everyone")
            .anonymous(false)
            .authorId(userSetup.getUser().getId())
            .visibility(PUBLIC);

    var hiPost = postsApi.createPost(toBeCreated);

    assertNotNull(hiPost.getId());
    assertEquals(toBeCreated.getContent(), hiPost.getContent());
  }

  @Test
  void user_create_others_post_ko() throws FirebaseAuthException {
    var otherUser = userRegistration.registerWithFirebase(someUser(), someToken());

    var postsApi = new PostsApi(anApiClient(userSetup.getToken()));
    var toBeCreated =
        new CreatePost()
            .content("Hi everyone")
            .anonymous(false)
            .authorId(otherUser.getId())
            .visibility(PUBLIC);

    assertThrowsForbiddenException(() -> postsApi.createPost(toBeCreated));
  }

  @Test
  void user_create_random_post_then_other_get_ok() throws ApiException, FirebaseAuthException {
    var postsApi = new PostsApi(anApiClient(userSetup.getToken()));
    var senderId = userSetup.getUser().getId();
    var toBeCreated =
        new CreatePost()
            .content("Hi everyone")
            .anonymous(false)
            .authorId(senderId)
            .visibility(PUBLIC);

    var hiPost = postsApi.createPost(toBeCreated);

    assertNotNull(hiPost.getId());
    assertEquals(toBeCreated.getContent(), hiPost.getContent());

    var theOtherUserToken = someToken();
    userRegistration.registerWithFirebase(someUser(), theOtherUserToken);
    var theOtherUserPostsApi = new PostsApi(anApiClient(theOtherUserToken));

    var posts = theOtherUserPostsApi.userPosts(senderId);

    assertEquals(1, posts.size());
    var post = posts.getFirst();
    assertNotNull(post);
    assertEquals(toBeCreated.getContent(), post.getContent());
  }
}

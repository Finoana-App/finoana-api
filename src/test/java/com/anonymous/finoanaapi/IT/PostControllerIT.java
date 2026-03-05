package com.anonymous.finoanaapi.IT;

import static com.anonymous.finoanaapi.controllers.model.PostVisibility.PUBLIC;
import static com.anonymous.finoanaapi.utils.DummyToken.someToken;
import static com.anonymous.finoanaapi.utils.DummyUser.someUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.anonymous.finoanaapi.config.TestConfig;
import com.anonymous.finoanaapi.controllers.api.PostsApi;
import com.anonymous.finoanaapi.controllers.client.ApiException;
import com.anonymous.finoanaapi.controllers.model.CreatePost;
import com.anonymous.finoanaapi.models.User;
import com.google.firebase.auth.FirebaseAuthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PostControllerIT extends TestConfig {
  private User user;
  private String token;

  @BeforeEach
  void setUp() throws FirebaseAuthException {
    user = someUser();
    token = someToken();
    userRegistration.registerWithFirebase(user, token);
  }

  @Test
  void user_create_random_post_ok() throws ApiException {
    var postsApi = new PostsApi(anApiClient(token));
    var toBeCreated =
        new CreatePost()
            .content("Hi everyone")
            .anonymous(false)
            .authorId(user.getId())
            .visibility(PUBLIC);

    var hiPost = postsApi.createPost(toBeCreated);

    assertNotNull(hiPost.getId());
    assertEquals(toBeCreated.getContent(), hiPost.getContent());
  }

  @Test
  void user_create_random_post_then_other_get_ok() throws ApiException, FirebaseAuthException {
    var postsApi = new PostsApi(anApiClient(token));
    var senderId = user.getId();
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

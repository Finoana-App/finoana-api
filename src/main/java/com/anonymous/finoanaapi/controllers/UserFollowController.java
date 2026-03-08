package com.anonymous.finoanaapi.controllers;

import static com.anonymous.finoanaapi.utils.security.firebase.FirebaseFilter.getPrincipal;

import com.anonymous.finoanaapi.controllers.mapper.user.UserMapper;
import com.anonymous.finoanaapi.controllers.model.User;
import com.anonymous.finoanaapi.services.UserFollowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/follows")
public class UserFollowController {
  private final UserFollowService userFollowService;
  private final UserMapper userMapper;

  @PutMapping("/{user_id}")
  User addFollow(@PathVariable("user_id") String userId) {
    var follow = userFollowService.makeFollow(getPrincipal().getId(), userId);
    return userMapper.toRest(follow.getFollowing());
  }
}

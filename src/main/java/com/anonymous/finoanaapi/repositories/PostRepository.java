package com.anonymous.finoanaapi.repositories;

import com.anonymous.finoanaapi.models.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
  List<Post> getPostByAuthor_Id(String authorId);
}

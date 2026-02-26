package com.anonymous.finoanaapi.repositories.dao;

import static com.anonymous.finoanaapi.models.User.FIRST_NAME_ATTRIBUTE;
import static com.anonymous.finoanaapi.models.User.LAST_NAME_ATTRIBUTE;

import com.anonymous.finoanaapi.models.User;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDao {
  private final EntityManager entityManager;

  // TODO: create a criteria for more clean code
  public Page<User> findByCriteria(String name, Pageable pageable) {
    var pagedCriteriaBuilder = new PagedCriteriaBuilder<>(entityManager, User.class);

    pagedCriteriaBuilder.addPredicate(
        (r, c) -> {
          if (name != null) {
            var firstnamePredicate =
                c.like(c.lower(r.get(FIRST_NAME_ATTRIBUTE)), "%" + name.toLowerCase() + "%");
            var lastnamePredicate =
                c.like(c.lower(r.get(LAST_NAME_ATTRIBUTE)), "%" + name.toLowerCase() + "%");

            return Optional.of(c.or(firstnamePredicate, lastnamePredicate));
          }
          return Optional.empty();
        });

    return pagedCriteriaBuilder.retrieve(pageable);
  }
}

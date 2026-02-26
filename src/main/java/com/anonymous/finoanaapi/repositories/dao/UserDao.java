package com.anonymous.finoanaapi.repositories.dao;

import static com.anonymous.finoanaapi.models.User.FIRST_NAME_ATTRIBUTE;
import static com.anonymous.finoanaapi.models.User.LAST_NAME_ATTRIBUTE;

import com.anonymous.finoanaapi.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDao {
  private final EntityManager entityManager;

  // TODO: create a criteria for more clean code
  public Page<User> findByCriteria(String name, Pageable pageable) {
    var criteriaBuilder = entityManager.getCriteriaBuilder();
    var criteriaBuilderCount = entityManager.getCriteriaBuilder();
    var query = criteriaBuilder.createQuery(User.class);
    var queryCount = criteriaBuilder.createQuery(Long.class);
    var root = query.from(User.class);
    var rootCount = queryCount.from(User.class);
    queryCount.select(criteriaBuilder.count(rootCount));

    List<Predicate> predicates = new ArrayList<>();
    List<Predicate> predicatesCount = new ArrayList<>();

    if (name != null) {
      var firstnamePredicate =
          criteriaBuilder.like(
              criteriaBuilder.lower(root.get(FIRST_NAME_ATTRIBUTE)),
              "%" + name.toLowerCase() + "%");
      var lastnamePredicate =
          criteriaBuilder.like(
              criteriaBuilder.lower(root.get(LAST_NAME_ATTRIBUTE)), "%" + name.toLowerCase() + "%");

      var predicate = criteriaBuilder.or(firstnamePredicate, lastnamePredicate);
      predicates.add(predicate);

      var firstnamePredicateCount =
          criteriaBuilderCount.like(
              criteriaBuilderCount.lower(rootCount.get(FIRST_NAME_ATTRIBUTE)),
              "%" + name.toLowerCase() + "%");
      var lastnamePredicateCount =
          criteriaBuilderCount.like(
              criteriaBuilderCount.lower(rootCount.get(LAST_NAME_ATTRIBUTE)),
              "%" + name.toLowerCase() + "%");

      var predicateCount = criteriaBuilderCount.or(firstnamePredicateCount, lastnamePredicateCount);
      predicatesCount.add(predicateCount);
    }

    query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
    queryCount.where(criteriaBuilderCount.and(predicatesCount.toArray(new Predicate[0])));

    var offset = Math.toIntExact(pageable.getOffset());
    var resultList =
        entityManager
            .createQuery(query)
            .setFirstResult(offset)
            .setMaxResults(pageable.getPageSize())
            .getResultList();
    var resultCount = entityManager.createQuery(queryCount).getSingleResult();
    return new PageImpl<>(resultList, pageable, resultCount);
  }
}

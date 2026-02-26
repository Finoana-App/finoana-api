package com.anonymous.finoanaapi.repositories.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PagedCriteriaBuilder<T> {
  private final EntityManager entityManager;
  private final CriteriaBuilder criteriaBuilder;
  private final CriteriaQuery<T> query;
  private final CriteriaQuery<Long> queryCount;
  private final Root<T> root;
  private final Root<T> rootCount;
  private final List<Optional<Predicate>> predicates = new ArrayList<>();
  private final List<Optional<Predicate>> predicatesCount = new ArrayList<>();

  public PagedCriteriaBuilder(EntityManager entityManager, Class<T> rootClass) {
    this.entityManager = entityManager;

    this.criteriaBuilder = entityManager.getCriteriaBuilder();
    this.query = criteriaBuilder.createQuery(rootClass);
    this.queryCount = criteriaBuilder.createQuery(Long.class);
    this.root = query.from(rootClass);
    this.rootCount = queryCount.from(rootClass);

    queryCount.select(criteriaBuilder.count(rootCount));
  }

  public void addPredicate(
      BiFunction<Root<T>, CriteriaBuilder, Optional<Predicate>> predicateBuilder) {
    predicates.add(predicateBuilder.apply(root, criteriaBuilder));
    predicatesCount.add(predicateBuilder.apply(rootCount, criteriaBuilder));
  }

  private CriteriaQuery<T> getQuery() {
    var predicatesArray =
        predicates.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList()
            .toArray(new Predicate[0]);
    query.where(criteriaBuilder.and(predicatesArray));
    return query;
  }

  private CriteriaQuery<Long> getCountQuery() {
    var predicatesCountArray =
        predicatesCount.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList()
            .toArray(new Predicate[0]);
    queryCount.where(criteriaBuilder.and(predicatesCountArray));
    return queryCount;
  }

  public Page<T> retrieve(Pageable pageable) {
    var offset = Math.toIntExact(pageable.getOffset());
    var resultList =
        entityManager
            .createQuery(getQuery())
            .setFirstResult(offset)
            .setMaxResults(pageable.getPageSize())
            .getResultList();
    var resultCount = entityManager.createQuery(getCountQuery()).getSingleResult();
    return new PageImpl<T>(resultList, pageable, resultCount);
  }
}

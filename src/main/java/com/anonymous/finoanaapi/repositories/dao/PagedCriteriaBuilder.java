package com.anonymous.finoanaapi.repositories.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class PagedCriteriaBuilder {
  private final EntityManager entityManager;
  private final CriteriaBuilder criteriaBuilder;
  private final CriteriaQuery query;
  private final CriteriaQuery<Long> queryCount;
  private final Root root;
  private final Root rootCount;
  private final List<Predicate> predicates = new ArrayList<>();
  private final List<Predicate> predicatesCount = new ArrayList<>();

  public PagedCriteriaBuilder(EntityManager entityManager, Class criteriaClass, Class rootClass) {
    this.entityManager = entityManager;

    this.criteriaBuilder = entityManager.getCriteriaBuilder();
    this.query = criteriaBuilder.createQuery(criteriaClass);
    this.queryCount = criteriaBuilder.createQuery(Long.class);
    this.root = query.from(rootClass);
    this.rootCount = queryCount.from(rootClass);

    queryCount.select(criteriaBuilder.count(rootCount));
  }

  public void addPredicate(
      boolean apply, BiFunction<Root, CriteriaBuilder, Predicate> predicateBuilder) {
    if (apply) {
      predicates.add(predicateBuilder.apply(root, criteriaBuilder));
      predicatesCount.add(predicateBuilder.apply(rootCount, criteriaBuilder));
    }
  }

  public CriteriaQuery getQuery() {
    query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
    return query;
  }

  public CriteriaQuery<Long> getCountQuery() {
    queryCount.where(criteriaBuilder.and(predicatesCount.toArray(new Predicate[0])));
    return queryCount;
  }
}

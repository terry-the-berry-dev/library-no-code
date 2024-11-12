package com.lighthouse.library.model.data;

import com.lighthouse.library.model.model.Author;
import com.lighthouse.library.model.model.AuthorToBook;
import com.lighthouse.library.model.model.AuthorToBook_;
import com.lighthouse.library.model.model.Author_;
import com.lighthouse.library.model.request.AuthorFilter;
import com.lighthouse.library.model.security.UserSecurityContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuthorRepository {

  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  /**
   * @param authorFilter Object Used to List Author
   * @param securityContext
   * @return List of Author
   */
  public List<Author> listAllAuthors(
      AuthorFilter authorFilter, UserSecurityContext securityContext) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Author> q = cb.createQuery(Author.class);
    Root<Author> r = q.from(Author.class);
    List<Predicate> preds = new ArrayList<>();
    addAuthorPredicate(authorFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));

    TypedQuery<Author> query = em.createQuery(q);

    if (authorFilter.getPageSize() != null
        && authorFilter.getCurrentPage() != null
        && authorFilter.getPageSize() > 0
        && authorFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(authorFilter.getPageSize() * authorFilter.getCurrentPage())
          .setMaxResults(authorFilter.getPageSize());
    }
    return query.getResultList();
  }

  public <T extends Author> void addAuthorPredicate(
      AuthorFilter authorFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    if (authorFilter.getDeletedLike() != null && !authorFilter.getDeletedLike().isEmpty()) {
      preds.add(cb.like(r.get(Author_.deleted), authorFilter.getDeletedLike()));
    }

    if (authorFilter.getDeleted() != null && !authorFilter.getDeleted().isEmpty()) {
      preds.add(r.get(Author_.deleted).in(authorFilter.getDeleted()));
    }

    if (authorFilter.getNameLike() != null && !authorFilter.getNameLike().isEmpty()) {
      preds.add(cb.like(r.get(Author_.name), authorFilter.getNameLike()));
    }

    if (authorFilter.getName() != null && !authorFilter.getName().isEmpty()) {
      preds.add(r.get(Author_.name).in(authorFilter.getName()));
    }

    if (authorFilter.getAuthorAuthorToBookses() != null
        && !authorFilter.getAuthorAuthorToBookses().isEmpty()) {
      Set<String> ids =
          authorFilter.getAuthorAuthorToBookses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, AuthorToBook> join = r.join(Author_.authorAuthorToBooks);
      preds.add(join.get(AuthorToBook_.id).in(ids));
    }

    if (authorFilter.getId() != null && !authorFilter.getId().isEmpty()) {
      preds.add(r.get(Author_.id).in(authorFilter.getId()));
    }
  }

  /**
   * @param authorFilter Object Used to List Author
   * @param securityContext
   * @return count of Author
   */
  public Long countAllAuthors(AuthorFilter authorFilter, UserSecurityContext securityContext) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<Author> r = q.from(Author.class);
    List<Predicate> preds = new ArrayList<>();
    addAuthorPredicate(authorFilter, cb, q, r, preds, securityContext);
    q.select(cb.count(r)).where(preds.toArray(new Predicate[0]));
    TypedQuery<Long> query = em.createQuery(q);
    return query.getSingleResult();
  }

  public void remove(Object o) {
    em.remove(o);
  }

  public <T extends Author, I> List<T> listByIds(
      Class<T> c,
      SingularAttribute<? super T, I> idField,
      Set<I> ids,
      UserSecurityContext securityContext) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<T> q = cb.createQuery(c);
    Root<T> r = q.from(c);
    List<Predicate> preds = new ArrayList<>();
    preds.add(r.get(idField).in(ids));

    q.select(r).where(preds.toArray(new Predicate[0]));
    return em.createQuery(q).getResultList();
  }

  public <T extends Author, I> T getByIdOrNull(
      Class<T> c, SingularAttribute<? super T, I> idField, I id) {
    return getByIdOrNull(c, idField, id, null);
  }

  public <T extends Author, I> List<T> listByIds(
      Class<T> c, SingularAttribute<? super T, I> idField, Set<I> ids) {
    return listByIds(c, idField, ids, null);
  }

  public <T extends Author, I> T getByIdOrNull(
      Class<T> c,
      SingularAttribute<? super T, I> idField,
      I id,
      UserSecurityContext securityContext) {
    return listByIds(c, idField, Collections.singleton(id), securityContext).stream()
        .findFirst()
        .orElse(null);
  }

  @Transactional
  public <T> T merge(T base) {
    T updated = em.merge(base);
    applicationEventPublisher.publishEvent(updated);
    return updated;
  }

  @Transactional
  public void massMerge(List<?> toMerge) {
    for (Object o : toMerge) {
      Object updated = em.merge(o);
      applicationEventPublisher.publishEvent(updated);
    }
  }
}

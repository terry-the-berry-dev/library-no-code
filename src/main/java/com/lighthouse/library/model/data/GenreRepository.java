package com.lighthouse.library.model.data;

import com.lighthouse.library.model.model.BookToGenre;
import com.lighthouse.library.model.model.BookToGenre_;
import com.lighthouse.library.model.model.Genre;
import com.lighthouse.library.model.model.Genre_;
import com.lighthouse.library.model.request.GenreFilter;
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
public class GenreRepository {

  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  /**
   * @param genreFilter Object Used to List Genre
   * @param securityContext
   * @return List of Genre
   */
  public List<Genre> listAllGenres(GenreFilter genreFilter, UserSecurityContext securityContext) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Genre> q = cb.createQuery(Genre.class);
    Root<Genre> r = q.from(Genre.class);
    List<Predicate> preds = new ArrayList<>();
    addGenrePredicate(genreFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));

    TypedQuery<Genre> query = em.createQuery(q);

    if (genreFilter.getPageSize() != null
        && genreFilter.getCurrentPage() != null
        && genreFilter.getPageSize() > 0
        && genreFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(genreFilter.getPageSize() * genreFilter.getCurrentPage())
          .setMaxResults(genreFilter.getPageSize());
    }
    return query.getResultList();
  }

  public <T extends Genre> void addGenrePredicate(
      GenreFilter genreFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    if (genreFilter.getId() != null && !genreFilter.getId().isEmpty()) {
      preds.add(r.get(Genre_.id).in(genreFilter.getId()));
    }

    if (genreFilter.getNameLike() != null && !genreFilter.getNameLike().isEmpty()) {
      preds.add(cb.like(r.get(Genre_.name), genreFilter.getNameLike()));
    }

    if (genreFilter.getDeleted() != null && !genreFilter.getDeleted().isEmpty()) {
      preds.add(r.get(Genre_.deleted).in(genreFilter.getDeleted()));
    }

    if (genreFilter.getName() != null && !genreFilter.getName().isEmpty()) {
      preds.add(r.get(Genre_.name).in(genreFilter.getName()));
    }

    if (genreFilter.getGenreBookToGenreses() != null
        && !genreFilter.getGenreBookToGenreses().isEmpty()) {
      Set<String> ids =
          genreFilter.getGenreBookToGenreses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, BookToGenre> join = r.join(Genre_.genreBookToGenres);
      preds.add(join.get(BookToGenre_.id).in(ids));
    }
  }

  /**
   * @param genreFilter Object Used to List Genre
   * @param securityContext
   * @return count of Genre
   */
  public Long countAllGenres(GenreFilter genreFilter, UserSecurityContext securityContext) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<Genre> r = q.from(Genre.class);
    List<Predicate> preds = new ArrayList<>();
    addGenrePredicate(genreFilter, cb, q, r, preds, securityContext);
    q.select(cb.count(r)).where(preds.toArray(new Predicate[0]));
    TypedQuery<Long> query = em.createQuery(q);
    return query.getSingleResult();
  }

  public void remove(Object o) {
    em.remove(o);
  }

  public <T extends Genre, I> List<T> listByIds(
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

  public <T extends Genre, I> T getByIdOrNull(
      Class<T> c, SingularAttribute<? super T, I> idField, I id) {
    return getByIdOrNull(c, idField, id, null);
  }

  public <T extends Genre, I> List<T> listByIds(
      Class<T> c, SingularAttribute<? super T, I> idField, Set<I> ids) {
    return listByIds(c, idField, ids, null);
  }

  public <T extends Genre, I> T getByIdOrNull(
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

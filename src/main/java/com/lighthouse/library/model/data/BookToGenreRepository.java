package com.lighthouse.library.model.data;

import com.lighthouse.library.model.model.Book;
import com.lighthouse.library.model.model.BookToGenre;
import com.lighthouse.library.model.model.BookToGenre_;
import com.lighthouse.library.model.model.Book_;
import com.lighthouse.library.model.model.Genre;
import com.lighthouse.library.model.model.Genre_;
import com.lighthouse.library.model.request.BookToGenreFilter;
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
public class BookToGenreRepository {

  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  /**
   * @param bookToGenreFilter Object Used to List BookToGenre
   * @param securityContext
   * @return List of BookToGenre
   */
  public List<BookToGenre> listAllBookToGenres(
      BookToGenreFilter bookToGenreFilter, UserSecurityContext securityContext) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<BookToGenre> q = cb.createQuery(BookToGenre.class);
    Root<BookToGenre> r = q.from(BookToGenre.class);
    List<Predicate> preds = new ArrayList<>();
    addBookToGenrePredicate(bookToGenreFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));

    TypedQuery<BookToGenre> query = em.createQuery(q);

    if (bookToGenreFilter.getPageSize() != null
        && bookToGenreFilter.getCurrentPage() != null
        && bookToGenreFilter.getPageSize() > 0
        && bookToGenreFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(bookToGenreFilter.getPageSize() * bookToGenreFilter.getCurrentPage())
          .setMaxResults(bookToGenreFilter.getPageSize());
    }
    return query.getResultList();
  }

  public <T extends BookToGenre> void addBookToGenrePredicate(
      BookToGenreFilter bookToGenreFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    if (bookToGenreFilter.getId() != null && !bookToGenreFilter.getId().isEmpty()) {
      preds.add(r.get(BookToGenre_.id).in(bookToGenreFilter.getId()));
    }

    if (bookToGenreFilter.getGenres() != null && !bookToGenreFilter.getGenres().isEmpty()) {
      Set<String> ids =
          bookToGenreFilter.getGenres().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Genre> join = r.join(BookToGenre_.genre);
      preds.add(join.get(Genre_.id).in(ids));
    }

    if (bookToGenreFilter.getBooks() != null && !bookToGenreFilter.getBooks().isEmpty()) {
      Set<String> ids =
          bookToGenreFilter.getBooks().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Book> join = r.join(BookToGenre_.book);
      preds.add(join.get(Book_.id).in(ids));
    }
  }

  /**
   * @param bookToGenreFilter Object Used to List BookToGenre
   * @param securityContext
   * @return count of BookToGenre
   */
  public Long countAllBookToGenres(
      BookToGenreFilter bookToGenreFilter, UserSecurityContext securityContext) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<BookToGenre> r = q.from(BookToGenre.class);
    List<Predicate> preds = new ArrayList<>();
    addBookToGenrePredicate(bookToGenreFilter, cb, q, r, preds, securityContext);
    q.select(cb.count(r)).where(preds.toArray(new Predicate[0]));
    TypedQuery<Long> query = em.createQuery(q);
    return query.getSingleResult();
  }

  public void remove(Object o) {
    em.remove(o);
  }

  public <T extends BookToGenre, I> List<T> listByIds(
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

  public <T extends BookToGenre, I> T getByIdOrNull(
      Class<T> c, SingularAttribute<? super T, I> idField, I id) {
    return getByIdOrNull(c, idField, id, null);
  }

  public <T extends BookToGenre, I> List<T> listByIds(
      Class<T> c, SingularAttribute<? super T, I> idField, Set<I> ids) {
    return listByIds(c, idField, ids, null);
  }

  public <T extends BookToGenre, I> T getByIdOrNull(
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

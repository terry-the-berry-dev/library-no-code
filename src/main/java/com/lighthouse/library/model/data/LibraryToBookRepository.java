package com.lighthouse.library.model.data;

import com.lighthouse.library.model.model.Book;
import com.lighthouse.library.model.model.Book_;
import com.lighthouse.library.model.model.Library;
import com.lighthouse.library.model.model.LibraryToBook;
import com.lighthouse.library.model.model.LibraryToBook_;
import com.lighthouse.library.model.model.Library_;
import com.lighthouse.library.model.request.LibraryToBookFilter;
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
public class LibraryToBookRepository {

  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  /**
   * @param libraryToBookFilter Object Used to List LibraryToBook
   * @param securityContext
   * @return List of LibraryToBook
   */
  public List<LibraryToBook> listAllLibraryToBooks(
      LibraryToBookFilter libraryToBookFilter, UserSecurityContext securityContext) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<LibraryToBook> q = cb.createQuery(LibraryToBook.class);
    Root<LibraryToBook> r = q.from(LibraryToBook.class);
    List<Predicate> preds = new ArrayList<>();
    addLibraryToBookPredicate(libraryToBookFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));

    TypedQuery<LibraryToBook> query = em.createQuery(q);

    if (libraryToBookFilter.getPageSize() != null
        && libraryToBookFilter.getCurrentPage() != null
        && libraryToBookFilter.getPageSize() > 0
        && libraryToBookFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(libraryToBookFilter.getPageSize() * libraryToBookFilter.getCurrentPage())
          .setMaxResults(libraryToBookFilter.getPageSize());
    }
    return query.getResultList();
  }

  public <T extends LibraryToBook> void addLibraryToBookPredicate(
      LibraryToBookFilter libraryToBookFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    if (libraryToBookFilter.getBooks() != null && !libraryToBookFilter.getBooks().isEmpty()) {
      Set<String> ids =
          libraryToBookFilter.getBooks().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Book> join = r.join(LibraryToBook_.book);
      preds.add(join.get(Book_.id).in(ids));
    }

    if (libraryToBookFilter.getLibraries() != null
        && !libraryToBookFilter.getLibraries().isEmpty()) {
      Set<String> ids =
          libraryToBookFilter.getLibraries().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Library> join = r.join(LibraryToBook_.library);
      preds.add(join.get(Library_.id).in(ids));
    }

    if (libraryToBookFilter.getId() != null && !libraryToBookFilter.getId().isEmpty()) {
      preds.add(r.get(LibraryToBook_.id).in(libraryToBookFilter.getId()));
    }
  }

  /**
   * @param libraryToBookFilter Object Used to List LibraryToBook
   * @param securityContext
   * @return count of LibraryToBook
   */
  public Long countAllLibraryToBooks(
      LibraryToBookFilter libraryToBookFilter, UserSecurityContext securityContext) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<LibraryToBook> r = q.from(LibraryToBook.class);
    List<Predicate> preds = new ArrayList<>();
    addLibraryToBookPredicate(libraryToBookFilter, cb, q, r, preds, securityContext);
    q.select(cb.count(r)).where(preds.toArray(new Predicate[0]));
    TypedQuery<Long> query = em.createQuery(q);
    return query.getSingleResult();
  }

  public void remove(Object o) {
    em.remove(o);
  }

  public <T extends LibraryToBook, I> List<T> listByIds(
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

  public <T extends LibraryToBook, I> T getByIdOrNull(
      Class<T> c, SingularAttribute<? super T, I> idField, I id) {
    return getByIdOrNull(c, idField, id, null);
  }

  public <T extends LibraryToBook, I> List<T> listByIds(
      Class<T> c, SingularAttribute<? super T, I> idField, Set<I> ids) {
    return listByIds(c, idField, ids, null);
  }

  public <T extends LibraryToBook, I> T getByIdOrNull(
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

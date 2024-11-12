package com.lighthouse.library.model.data;

import com.lighthouse.library.model.model.Lender;
import com.lighthouse.library.model.model.Lender_;
import com.lighthouse.library.model.model.Library;
import com.lighthouse.library.model.model.LibraryToLender;
import com.lighthouse.library.model.model.LibraryToLender_;
import com.lighthouse.library.model.model.Library_;
import com.lighthouse.library.model.request.LibraryToLenderFilter;
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
public class LibraryToLenderRepository {

  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  /**
   * @param libraryToLenderFilter Object Used to List LibraryToLender
   * @param securityContext
   * @return List of LibraryToLender
   */
  public List<LibraryToLender> listAllLibraryToLenders(
      LibraryToLenderFilter libraryToLenderFilter, UserSecurityContext securityContext) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<LibraryToLender> q = cb.createQuery(LibraryToLender.class);
    Root<LibraryToLender> r = q.from(LibraryToLender.class);
    List<Predicate> preds = new ArrayList<>();
    addLibraryToLenderPredicate(libraryToLenderFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));

    TypedQuery<LibraryToLender> query = em.createQuery(q);

    if (libraryToLenderFilter.getPageSize() != null
        && libraryToLenderFilter.getCurrentPage() != null
        && libraryToLenderFilter.getPageSize() > 0
        && libraryToLenderFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(
              libraryToLenderFilter.getPageSize() * libraryToLenderFilter.getCurrentPage())
          .setMaxResults(libraryToLenderFilter.getPageSize());
    }
    return query.getResultList();
  }

  public <T extends LibraryToLender> void addLibraryToLenderPredicate(
      LibraryToLenderFilter libraryToLenderFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    if (libraryToLenderFilter.getLenders() != null
        && !libraryToLenderFilter.getLenders().isEmpty()) {
      Set<String> ids =
          libraryToLenderFilter.getLenders().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Lender> join = r.join(LibraryToLender_.lender);
      preds.add(join.get(Lender_.id).in(ids));
    }

    if (libraryToLenderFilter.getLibraries() != null
        && !libraryToLenderFilter.getLibraries().isEmpty()) {
      Set<String> ids =
          libraryToLenderFilter.getLibraries().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Library> join = r.join(LibraryToLender_.library);
      preds.add(join.get(Library_.id).in(ids));
    }

    if (libraryToLenderFilter.getId() != null && !libraryToLenderFilter.getId().isEmpty()) {
      preds.add(r.get(LibraryToLender_.id).in(libraryToLenderFilter.getId()));
    }
  }

  /**
   * @param libraryToLenderFilter Object Used to List LibraryToLender
   * @param securityContext
   * @return count of LibraryToLender
   */
  public Long countAllLibraryToLenders(
      LibraryToLenderFilter libraryToLenderFilter, UserSecurityContext securityContext) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<LibraryToLender> r = q.from(LibraryToLender.class);
    List<Predicate> preds = new ArrayList<>();
    addLibraryToLenderPredicate(libraryToLenderFilter, cb, q, r, preds, securityContext);
    q.select(cb.count(r)).where(preds.toArray(new Predicate[0]));
    TypedQuery<Long> query = em.createQuery(q);
    return query.getSingleResult();
  }

  public void remove(Object o) {
    em.remove(o);
  }

  public <T extends LibraryToLender, I> List<T> listByIds(
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

  public <T extends LibraryToLender, I> T getByIdOrNull(
      Class<T> c, SingularAttribute<? super T, I> idField, I id) {
    return getByIdOrNull(c, idField, id, null);
  }

  public <T extends LibraryToLender, I> List<T> listByIds(
      Class<T> c, SingularAttribute<? super T, I> idField, Set<I> ids) {
    return listByIds(c, idField, ids, null);
  }

  public <T extends LibraryToLender, I> T getByIdOrNull(
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

package com.lighthouse.library.model.data;

import com.lighthouse.library.model.model.Book;
import com.lighthouse.library.model.model.Book_;
import com.lighthouse.library.model.model.Lender;
import com.lighthouse.library.model.model.LenderToBook;
import com.lighthouse.library.model.model.LenderToBook_;
import com.lighthouse.library.model.model.Lender_;
import com.lighthouse.library.model.request.LenderToBookFilter;
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
public class LenderToBookRepository {

  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  /**
   * @param lenderToBookFilter Object Used to List LenderToBook
   * @param securityContext
   * @return List of LenderToBook
   */
  public List<LenderToBook> listAllLenderToBooks(
      LenderToBookFilter lenderToBookFilter, UserSecurityContext securityContext) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<LenderToBook> q = cb.createQuery(LenderToBook.class);
    Root<LenderToBook> r = q.from(LenderToBook.class);
    List<Predicate> preds = new ArrayList<>();
    addLenderToBookPredicate(lenderToBookFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));

    TypedQuery<LenderToBook> query = em.createQuery(q);

    if (lenderToBookFilter.getPageSize() != null
        && lenderToBookFilter.getCurrentPage() != null
        && lenderToBookFilter.getPageSize() > 0
        && lenderToBookFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(lenderToBookFilter.getPageSize() * lenderToBookFilter.getCurrentPage())
          .setMaxResults(lenderToBookFilter.getPageSize());
    }
    return query.getResultList();
  }

  public <T extends LenderToBook> void addLenderToBookPredicate(
      LenderToBookFilter lenderToBookFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    if (lenderToBookFilter.getBooks() != null && !lenderToBookFilter.getBooks().isEmpty()) {
      Set<String> ids =
          lenderToBookFilter.getBooks().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Book> join = r.join(LenderToBook_.book);
      preds.add(join.get(Book_.id).in(ids));
    }

    if (lenderToBookFilter.getLenders() != null && !lenderToBookFilter.getLenders().isEmpty()) {
      Set<String> ids =
          lenderToBookFilter.getLenders().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, Lender> join = r.join(LenderToBook_.lender);
      preds.add(join.get(Lender_.id).in(ids));
    }

    if (lenderToBookFilter.getId() != null && !lenderToBookFilter.getId().isEmpty()) {
      preds.add(r.get(LenderToBook_.id).in(lenderToBookFilter.getId()));
    }
  }

  /**
   * @param lenderToBookFilter Object Used to List LenderToBook
   * @param securityContext
   * @return count of LenderToBook
   */
  public Long countAllLenderToBooks(
      LenderToBookFilter lenderToBookFilter, UserSecurityContext securityContext) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<LenderToBook> r = q.from(LenderToBook.class);
    List<Predicate> preds = new ArrayList<>();
    addLenderToBookPredicate(lenderToBookFilter, cb, q, r, preds, securityContext);
    q.select(cb.count(r)).where(preds.toArray(new Predicate[0]));
    TypedQuery<Long> query = em.createQuery(q);
    return query.getSingleResult();
  }

  public void remove(Object o) {
    em.remove(o);
  }

  public <T extends LenderToBook, I> List<T> listByIds(
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

  public <T extends LenderToBook, I> T getByIdOrNull(
      Class<T> c, SingularAttribute<? super T, I> idField, I id) {
    return getByIdOrNull(c, idField, id, null);
  }

  public <T extends LenderToBook, I> List<T> listByIds(
      Class<T> c, SingularAttribute<? super T, I> idField, Set<I> ids) {
    return listByIds(c, idField, ids, null);
  }

  public <T extends LenderToBook, I> T getByIdOrNull(
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

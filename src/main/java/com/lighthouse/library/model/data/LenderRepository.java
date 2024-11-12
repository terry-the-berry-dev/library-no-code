package com.lighthouse.library.model.data;

import com.lighthouse.library.model.model.Lender;
import com.lighthouse.library.model.model.LenderToBook;
import com.lighthouse.library.model.model.LenderToBook_;
import com.lighthouse.library.model.model.Lender_;
import com.lighthouse.library.model.model.LibraryToLender;
import com.lighthouse.library.model.model.LibraryToLender_;
import com.lighthouse.library.model.request.LenderFilter;
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
public class LenderRepository {

  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  /**
   * @param lenderFilter Object Used to List Lender
   * @param securityContext
   * @return List of Lender
   */
  public List<Lender> listAllLenders(
      LenderFilter lenderFilter, UserSecurityContext securityContext) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Lender> q = cb.createQuery(Lender.class);
    Root<Lender> r = q.from(Lender.class);
    List<Predicate> preds = new ArrayList<>();
    addLenderPredicate(lenderFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));

    TypedQuery<Lender> query = em.createQuery(q);

    if (lenderFilter.getPageSize() != null
        && lenderFilter.getCurrentPage() != null
        && lenderFilter.getPageSize() > 0
        && lenderFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(lenderFilter.getPageSize() * lenderFilter.getCurrentPage())
          .setMaxResults(lenderFilter.getPageSize());
    }
    return query.getResultList();
  }

  public <T extends Lender> void addLenderPredicate(
      LenderFilter lenderFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    if (lenderFilter.getLenderLibraryToLenderses() != null
        && !lenderFilter.getLenderLibraryToLenderses().isEmpty()) {
      Set<String> ids =
          lenderFilter.getLenderLibraryToLenderses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, LibraryToLender> join = r.join(Lender_.lenderLibraryToLenders);
      preds.add(join.get(LibraryToLender_.id).in(ids));
    }

    if (lenderFilter.getLenderLenderToBookses() != null
        && !lenderFilter.getLenderLenderToBookses().isEmpty()) {
      Set<String> ids =
          lenderFilter.getLenderLenderToBookses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, LenderToBook> join = r.join(Lender_.lenderLenderToBooks);
      preds.add(join.get(LenderToBook_.id).in(ids));
    }

    if (lenderFilter.getDeleted() != null && !lenderFilter.getDeleted().isEmpty()) {
      preds.add(r.get(Lender_.deleted).in(lenderFilter.getDeleted()));
    }

    if (lenderFilter.getNameLike() != null && !lenderFilter.getNameLike().isEmpty()) {
      preds.add(cb.like(r.get(Lender_.name), lenderFilter.getNameLike()));
    }

    if (lenderFilter.getId() != null && !lenderFilter.getId().isEmpty()) {
      preds.add(r.get(Lender_.id).in(lenderFilter.getId()));
    }

    if (lenderFilter.getName() != null && !lenderFilter.getName().isEmpty()) {
      preds.add(r.get(Lender_.name).in(lenderFilter.getName()));
    }
  }

  /**
   * @param lenderFilter Object Used to List Lender
   * @param securityContext
   * @return count of Lender
   */
  public Long countAllLenders(LenderFilter lenderFilter, UserSecurityContext securityContext) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<Lender> r = q.from(Lender.class);
    List<Predicate> preds = new ArrayList<>();
    addLenderPredicate(lenderFilter, cb, q, r, preds, securityContext);
    q.select(cb.count(r)).where(preds.toArray(new Predicate[0]));
    TypedQuery<Long> query = em.createQuery(q);
    return query.getSingleResult();
  }

  public void remove(Object o) {
    em.remove(o);
  }

  public <T extends Lender, I> List<T> listByIds(
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

  public <T extends Lender, I> T getByIdOrNull(
      Class<T> c, SingularAttribute<? super T, I> idField, I id) {
    return getByIdOrNull(c, idField, id, null);
  }

  public <T extends Lender, I> List<T> listByIds(
      Class<T> c, SingularAttribute<? super T, I> idField, Set<I> ids) {
    return listByIds(c, idField, ids, null);
  }

  public <T extends Lender, I> T getByIdOrNull(
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

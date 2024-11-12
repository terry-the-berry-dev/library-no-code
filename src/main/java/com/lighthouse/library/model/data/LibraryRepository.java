package com.lighthouse.library.model.data;

import com.lighthouse.library.model.model.Library;
import com.lighthouse.library.model.model.LibraryToBook;
import com.lighthouse.library.model.model.LibraryToBook_;
import com.lighthouse.library.model.model.LibraryToLender;
import com.lighthouse.library.model.model.LibraryToLender_;
import com.lighthouse.library.model.model.Library_;
import com.lighthouse.library.model.request.LibraryFilter;
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
public class LibraryRepository {

  @PersistenceContext private EntityManager em;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  /**
   * @param libraryFilter Object Used to List Library
   * @param securityContext
   * @return List of Library
   */
  public List<Library> listAllLibraries(
      LibraryFilter libraryFilter, UserSecurityContext securityContext) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Library> q = cb.createQuery(Library.class);
    Root<Library> r = q.from(Library.class);
    List<Predicate> preds = new ArrayList<>();
    addLibraryPredicate(libraryFilter, cb, q, r, preds, securityContext);
    q.select(r).where(preds.toArray(new Predicate[0]));

    TypedQuery<Library> query = em.createQuery(q);

    if (libraryFilter.getPageSize() != null
        && libraryFilter.getCurrentPage() != null
        && libraryFilter.getPageSize() > 0
        && libraryFilter.getCurrentPage() > -1) {
      query
          .setFirstResult(libraryFilter.getPageSize() * libraryFilter.getCurrentPage())
          .setMaxResults(libraryFilter.getPageSize());
    }
    return query.getResultList();
  }

  public <T extends Library> void addLibraryPredicate(
      LibraryFilter libraryFilter,
      CriteriaBuilder cb,
      CommonAbstractCriteria q,
      From<?, T> r,
      List<Predicate> preds,
      UserSecurityContext securityContext) {

    if (libraryFilter.getName() != null && !libraryFilter.getName().isEmpty()) {
      preds.add(r.get(Library_.name).in(libraryFilter.getName()));
    }

    if (libraryFilter.getId() != null && !libraryFilter.getId().isEmpty()) {
      preds.add(r.get(Library_.id).in(libraryFilter.getId()));
    }

    if (libraryFilter.getLibraryLibraryToBookses() != null
        && !libraryFilter.getLibraryLibraryToBookses().isEmpty()) {
      Set<String> ids =
          libraryFilter.getLibraryLibraryToBookses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, LibraryToBook> join = r.join(Library_.libraryLibraryToBooks);
      preds.add(join.get(LibraryToBook_.id).in(ids));
    }

    if (libraryFilter.getDeleted() != null && !libraryFilter.getDeleted().isEmpty()) {
      preds.add(r.get(Library_.deleted).in(libraryFilter.getDeleted()));
    }

    if (libraryFilter.getLibraryLibraryToLenderses() != null
        && !libraryFilter.getLibraryLibraryToLenderses().isEmpty()) {
      Set<String> ids =
          libraryFilter.getLibraryLibraryToLenderses().parallelStream()
              .map(f -> f.getId())
              .collect(Collectors.toSet());
      Join<T, LibraryToLender> join = r.join(Library_.libraryLibraryToLenders);
      preds.add(join.get(LibraryToLender_.id).in(ids));
    }

    if (libraryFilter.getNameLike() != null && !libraryFilter.getNameLike().isEmpty()) {
      preds.add(cb.like(r.get(Library_.name), libraryFilter.getNameLike()));
    }
  }

  /**
   * @param libraryFilter Object Used to List Library
   * @param securityContext
   * @return count of Library
   */
  public Long countAllLibraries(LibraryFilter libraryFilter, UserSecurityContext securityContext) {

    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> q = cb.createQuery(Long.class);
    Root<Library> r = q.from(Library.class);
    List<Predicate> preds = new ArrayList<>();
    addLibraryPredicate(libraryFilter, cb, q, r, preds, securityContext);
    q.select(cb.count(r)).where(preds.toArray(new Predicate[0]));
    TypedQuery<Long> query = em.createQuery(q);
    return query.getSingleResult();
  }

  public void remove(Object o) {
    em.remove(o);
  }

  public <T extends Library, I> List<T> listByIds(
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

  public <T extends Library, I> T getByIdOrNull(
      Class<T> c, SingularAttribute<? super T, I> idField, I id) {
    return getByIdOrNull(c, idField, id, null);
  }

  public <T extends Library, I> List<T> listByIds(
      Class<T> c, SingularAttribute<? super T, I> idField, Set<I> ids) {
    return listByIds(c, idField, ids, null);
  }

  public <T extends Library, I> T getByIdOrNull(
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

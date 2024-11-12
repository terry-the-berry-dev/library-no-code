package com.lighthouse.library.model.service;

import com.lighthouse.library.model.data.LenderToBookRepository;
import com.lighthouse.library.model.model.LenderToBook;
import com.lighthouse.library.model.model.LenderToBook_;
import com.lighthouse.library.model.request.LenderToBookCreate;
import com.lighthouse.library.model.request.LenderToBookFilter;
import com.lighthouse.library.model.request.LenderToBookUpdate;
import com.lighthouse.library.model.response.PaginationResponse;
import com.lighthouse.library.model.security.UserSecurityContext;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class LenderToBookService {

  @Autowired private LenderToBookRepository repository;

  /**
   * @param lenderToBookCreate Object Used to Create LenderToBook
   * @param securityContext
   * @return created LenderToBook
   */
  public LenderToBook createLenderToBook(
      LenderToBookCreate lenderToBookCreate, UserSecurityContext securityContext) {

    LenderToBook lenderToBook = createLenderToBookNoMerge(lenderToBookCreate, securityContext);
    lenderToBook = this.repository.merge(lenderToBook);
    return lenderToBook;
  }

  /**
   * @param lenderToBookCreate Object Used to Create LenderToBook
   * @param securityContext
   * @return created LenderToBook unmerged
   */
  public LenderToBook createLenderToBookNoMerge(
      LenderToBookCreate lenderToBookCreate, UserSecurityContext securityContext) {

    LenderToBook lenderToBook = new LenderToBook();
    lenderToBook.setId(UUID.randomUUID().toString());

    updateLenderToBookNoMerge(lenderToBook, lenderToBookCreate);

    return lenderToBook;
  }

  /**
   * @param lenderToBookCreate Object Used to Create LenderToBook
   * @param lenderToBook
   * @return if lenderToBook was updated
   */
  public boolean updateLenderToBookNoMerge(
      LenderToBook lenderToBook, LenderToBookCreate lenderToBookCreate) {

    boolean update = false;

    if (lenderToBookCreate.getLender() != null
        && (lenderToBook.getLender() == null
            || !lenderToBookCreate.getLender().getId().equals(lenderToBook.getLender().getId()))) {
      lenderToBook.setLender(lenderToBookCreate.getLender());
      update = true;
    }

    if (lenderToBookCreate.getBook() != null
        && (lenderToBook.getBook() == null
            || !lenderToBookCreate.getBook().getId().equals(lenderToBook.getBook().getId()))) {
      lenderToBook.setBook(lenderToBookCreate.getBook());
      update = true;
    }

    return update;
  }

  /**
   * @param lenderToBookUpdate
   * @param securityContext
   * @return lenderToBook
   */
  public LenderToBook updateLenderToBook(
      LenderToBookUpdate lenderToBookUpdate, UserSecurityContext securityContext) {

    LenderToBook lenderToBook = lenderToBookUpdate.getLenderToBook();

    if (updateLenderToBookNoMerge(lenderToBook, lenderToBookUpdate)) {
      lenderToBook = this.repository.merge(lenderToBook);
    }

    return lenderToBook;
  }

  /**
   * @param lenderToBookFilter Object Used to List LenderToBook
   * @param securityContext
   * @return PaginationResponse<LenderToBook> containing paging information for LenderToBook
   */
  public PaginationResponse<LenderToBook> getAllLenderToBooks(
      LenderToBookFilter lenderToBookFilter, UserSecurityContext securityContext) {

    List<LenderToBook> list = listAllLenderToBooks(lenderToBookFilter, securityContext);
    long count = this.repository.countAllLenderToBooks(lenderToBookFilter, securityContext);

    return new PaginationResponse<>(list, lenderToBookFilter.getPageSize(), count);
  }

  /**
   * @param lenderToBookFilter Object Used to List LenderToBook
   * @param securityContext
   * @return List of LenderToBook
   */
  public List<LenderToBook> listAllLenderToBooks(
      LenderToBookFilter lenderToBookFilter, UserSecurityContext securityContext) {

    return this.repository.listAllLenderToBooks(lenderToBookFilter, securityContext);
  }

  public LenderToBook deleteLenderToBook(String id, UserSecurityContext securityContext) {

    LenderToBook lenderToBook =
        this.repository.getByIdOrNull(LenderToBook.class, LenderToBook_.id, id, securityContext);

    if (lenderToBook == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LenderToBook not found");
    }

    this.repository.remove(lenderToBook);

    return lenderToBook;
  }

  public <T extends LenderToBook, I> List<T> listByIds(
      Class<T> c,
      SingularAttribute<? super T, I> idField,
      Set<I> ids,
      UserSecurityContext securityContext) {
    return repository.listByIds(c, idField, ids, securityContext);
  }

  public <T extends LenderToBook, I> T getByIdOrNull(
      Class<T> c,
      SingularAttribute<? super T, I> idField,
      I id,
      UserSecurityContext securityContext) {
    return repository.getByIdOrNull(c, idField, id, securityContext);
  }

  public <T extends LenderToBook, I> T getByIdOrNull(
      Class<T> c, SingularAttribute<? super T, I> idField, I id) {
    return repository.getByIdOrNull(c, idField, id);
  }

  public <T extends LenderToBook, I> List<T> listByIds(
      Class<T> c, SingularAttribute<? super T, I> idField, Set<I> ids) {
    return repository.listByIds(c, idField, ids);
  }

  public <T> T merge(T base) {
    return this.repository.merge(base);
  }

  public void massMerge(List<?> toMerge) {
    this.repository.massMerge(toMerge);
  }
}

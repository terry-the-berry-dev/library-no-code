package com.lighthouse.library.model.service;

import com.lighthouse.library.model.data.LibraryToBookRepository;
import com.lighthouse.library.model.model.LibraryToBook;
import com.lighthouse.library.model.model.LibraryToBook_;
import com.lighthouse.library.model.request.LibraryToBookCreate;
import com.lighthouse.library.model.request.LibraryToBookFilter;
import com.lighthouse.library.model.request.LibraryToBookUpdate;
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
public class LibraryToBookService {

  @Autowired private LibraryToBookRepository repository;

  /**
   * @param libraryToBookCreate Object Used to Create LibraryToBook
   * @param securityContext
   * @return created LibraryToBook
   */
  public LibraryToBook createLibraryToBook(
      LibraryToBookCreate libraryToBookCreate, UserSecurityContext securityContext) {

    LibraryToBook libraryToBook = createLibraryToBookNoMerge(libraryToBookCreate, securityContext);
    libraryToBook = this.repository.merge(libraryToBook);
    return libraryToBook;
  }

  /**
   * @param libraryToBookCreate Object Used to Create LibraryToBook
   * @param securityContext
   * @return created LibraryToBook unmerged
   */
  public LibraryToBook createLibraryToBookNoMerge(
      LibraryToBookCreate libraryToBookCreate, UserSecurityContext securityContext) {

    LibraryToBook libraryToBook = new LibraryToBook();
    libraryToBook.setId(UUID.randomUUID().toString());

    updateLibraryToBookNoMerge(libraryToBook, libraryToBookCreate);

    return libraryToBook;
  }

  /**
   * @param libraryToBookCreate Object Used to Create LibraryToBook
   * @param libraryToBook
   * @return if libraryToBook was updated
   */
  public boolean updateLibraryToBookNoMerge(
      LibraryToBook libraryToBook, LibraryToBookCreate libraryToBookCreate) {

    boolean update = false;

    if (libraryToBookCreate.getBook() != null
        && (libraryToBook.getBook() == null
            || !libraryToBookCreate.getBook().getId().equals(libraryToBook.getBook().getId()))) {
      libraryToBook.setBook(libraryToBookCreate.getBook());
      update = true;
    }

    if (libraryToBookCreate.getLibrary() != null
        && (libraryToBook.getLibrary() == null
            || !libraryToBookCreate
                .getLibrary()
                .getId()
                .equals(libraryToBook.getLibrary().getId()))) {
      libraryToBook.setLibrary(libraryToBookCreate.getLibrary());
      update = true;
    }

    return update;
  }

  /**
   * @param libraryToBookUpdate
   * @param securityContext
   * @return libraryToBook
   */
  public LibraryToBook updateLibraryToBook(
      LibraryToBookUpdate libraryToBookUpdate, UserSecurityContext securityContext) {

    LibraryToBook libraryToBook = libraryToBookUpdate.getLibraryToBook();

    if (updateLibraryToBookNoMerge(libraryToBook, libraryToBookUpdate)) {
      libraryToBook = this.repository.merge(libraryToBook);
    }

    return libraryToBook;
  }

  /**
   * @param libraryToBookFilter Object Used to List LibraryToBook
   * @param securityContext
   * @return PaginationResponse<LibraryToBook> containing paging information for LibraryToBook
   */
  public PaginationResponse<LibraryToBook> getAllLibraryToBooks(
      LibraryToBookFilter libraryToBookFilter, UserSecurityContext securityContext) {

    List<LibraryToBook> list = listAllLibraryToBooks(libraryToBookFilter, securityContext);
    long count = this.repository.countAllLibraryToBooks(libraryToBookFilter, securityContext);

    return new PaginationResponse<>(list, libraryToBookFilter.getPageSize(), count);
  }

  /**
   * @param libraryToBookFilter Object Used to List LibraryToBook
   * @param securityContext
   * @return List of LibraryToBook
   */
  public List<LibraryToBook> listAllLibraryToBooks(
      LibraryToBookFilter libraryToBookFilter, UserSecurityContext securityContext) {

    return this.repository.listAllLibraryToBooks(libraryToBookFilter, securityContext);
  }

  public LibraryToBook deleteLibraryToBook(String id, UserSecurityContext securityContext) {

    LibraryToBook libraryToBook =
        this.repository.getByIdOrNull(LibraryToBook.class, LibraryToBook_.id, id, securityContext);

    if (libraryToBook == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LibraryToBook not found");
    }

    this.repository.remove(libraryToBook);

    return libraryToBook;
  }

  public <T extends LibraryToBook, I> List<T> listByIds(
      Class<T> c,
      SingularAttribute<? super T, I> idField,
      Set<I> ids,
      UserSecurityContext securityContext) {
    return repository.listByIds(c, idField, ids, securityContext);
  }

  public <T extends LibraryToBook, I> T getByIdOrNull(
      Class<T> c,
      SingularAttribute<? super T, I> idField,
      I id,
      UserSecurityContext securityContext) {
    return repository.getByIdOrNull(c, idField, id, securityContext);
  }

  public <T extends LibraryToBook, I> T getByIdOrNull(
      Class<T> c, SingularAttribute<? super T, I> idField, I id) {
    return repository.getByIdOrNull(c, idField, id);
  }

  public <T extends LibraryToBook, I> List<T> listByIds(
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

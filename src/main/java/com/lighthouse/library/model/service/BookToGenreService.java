package com.lighthouse.library.model.service;

import com.lighthouse.library.model.data.BookToGenreRepository;
import com.lighthouse.library.model.model.BookToGenre;
import com.lighthouse.library.model.model.BookToGenre_;
import com.lighthouse.library.model.request.BookToGenreCreate;
import com.lighthouse.library.model.request.BookToGenreFilter;
import com.lighthouse.library.model.request.BookToGenreUpdate;
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
public class BookToGenreService {

  @Autowired private BookToGenreRepository repository;

  /**
   * @param bookToGenreCreate Object Used to Create BookToGenre
   * @param securityContext
   * @return created BookToGenre
   */
  public BookToGenre createBookToGenre(
      BookToGenreCreate bookToGenreCreate, UserSecurityContext securityContext) {

    BookToGenre bookToGenre = createBookToGenreNoMerge(bookToGenreCreate, securityContext);
    bookToGenre = this.repository.merge(bookToGenre);
    return bookToGenre;
  }

  /**
   * @param bookToGenreCreate Object Used to Create BookToGenre
   * @param securityContext
   * @return created BookToGenre unmerged
   */
  public BookToGenre createBookToGenreNoMerge(
      BookToGenreCreate bookToGenreCreate, UserSecurityContext securityContext) {

    BookToGenre bookToGenre = new BookToGenre();
    bookToGenre.setId(UUID.randomUUID().toString());

    updateBookToGenreNoMerge(bookToGenre, bookToGenreCreate);

    return bookToGenre;
  }

  /**
   * @param bookToGenreCreate Object Used to Create BookToGenre
   * @param bookToGenre
   * @return if bookToGenre was updated
   */
  public boolean updateBookToGenreNoMerge(
      BookToGenre bookToGenre, BookToGenreCreate bookToGenreCreate) {

    boolean update = false;

    if (bookToGenreCreate.getGenre() != null
        && (bookToGenre.getGenre() == null
            || !bookToGenreCreate.getGenre().getId().equals(bookToGenre.getGenre().getId()))) {
      bookToGenre.setGenre(bookToGenreCreate.getGenre());
      update = true;
    }

    if (bookToGenreCreate.getBook() != null
        && (bookToGenre.getBook() == null
            || !bookToGenreCreate.getBook().getId().equals(bookToGenre.getBook().getId()))) {
      bookToGenre.setBook(bookToGenreCreate.getBook());
      update = true;
    }

    return update;
  }

  /**
   * @param bookToGenreUpdate
   * @param securityContext
   * @return bookToGenre
   */
  public BookToGenre updateBookToGenre(
      BookToGenreUpdate bookToGenreUpdate, UserSecurityContext securityContext) {

    BookToGenre bookToGenre = bookToGenreUpdate.getBookToGenre();

    if (updateBookToGenreNoMerge(bookToGenre, bookToGenreUpdate)) {
      bookToGenre = this.repository.merge(bookToGenre);
    }

    return bookToGenre;
  }

  /**
   * @param bookToGenreFilter Object Used to List BookToGenre
   * @param securityContext
   * @return PaginationResponse<BookToGenre> containing paging information for BookToGenre
   */
  public PaginationResponse<BookToGenre> getAllBookToGenres(
      BookToGenreFilter bookToGenreFilter, UserSecurityContext securityContext) {

    List<BookToGenre> list = listAllBookToGenres(bookToGenreFilter, securityContext);
    long count = this.repository.countAllBookToGenres(bookToGenreFilter, securityContext);

    return new PaginationResponse<>(list, bookToGenreFilter.getPageSize(), count);
  }

  /**
   * @param bookToGenreFilter Object Used to List BookToGenre
   * @param securityContext
   * @return List of BookToGenre
   */
  public List<BookToGenre> listAllBookToGenres(
      BookToGenreFilter bookToGenreFilter, UserSecurityContext securityContext) {

    return this.repository.listAllBookToGenres(bookToGenreFilter, securityContext);
  }

  public BookToGenre deleteBookToGenre(String id, UserSecurityContext securityContext) {

    BookToGenre bookToGenre =
        this.repository.getByIdOrNull(BookToGenre.class, BookToGenre_.id, id, securityContext);

    if (bookToGenre == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "BookToGenre not found");
    }

    this.repository.remove(bookToGenre);

    return bookToGenre;
  }

  public <T extends BookToGenre, I> List<T> listByIds(
      Class<T> c,
      SingularAttribute<? super T, I> idField,
      Set<I> ids,
      UserSecurityContext securityContext) {
    return repository.listByIds(c, idField, ids, securityContext);
  }

  public <T extends BookToGenre, I> T getByIdOrNull(
      Class<T> c,
      SingularAttribute<? super T, I> idField,
      I id,
      UserSecurityContext securityContext) {
    return repository.getByIdOrNull(c, idField, id, securityContext);
  }

  public <T extends BookToGenre, I> T getByIdOrNull(
      Class<T> c, SingularAttribute<? super T, I> idField, I id) {
    return repository.getByIdOrNull(c, idField, id);
  }

  public <T extends BookToGenre, I> List<T> listByIds(
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

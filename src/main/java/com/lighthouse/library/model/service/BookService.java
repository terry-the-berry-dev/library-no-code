package com.lighthouse.library.model.service;

import com.lighthouse.library.model.data.BookRepository;
import com.lighthouse.library.model.model.Book;
import com.lighthouse.library.model.model.Book_;
import com.lighthouse.library.model.request.BookCreate;
import com.lighthouse.library.model.request.BookFilter;
import com.lighthouse.library.model.request.BookUpdate;
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
public class BookService {

  @Autowired private BookRepository repository;

  /**
   * @param bookCreate Object Used to Create Book
   * @param securityContext
   * @return created Book
   */
  public Book createBook(BookCreate bookCreate, UserSecurityContext securityContext) {

    Book book = createBookNoMerge(bookCreate, securityContext);
    book = this.repository.merge(book);
    return book;
  }

  /**
   * @param bookCreate Object Used to Create Book
   * @param securityContext
   * @return created Book unmerged
   */
  public Book createBookNoMerge(BookCreate bookCreate, UserSecurityContext securityContext) {

    Book book = new Book();
    book.setId(UUID.randomUUID().toString());

    updateBookNoMerge(book, bookCreate);

    return book;
  }

  /**
   * @param bookCreate Object Used to Create Book
   * @param book
   * @return if book was updated
   */
  public boolean updateBookNoMerge(Book book, BookCreate bookCreate) {

    boolean update = false;

    if (bookCreate.getTitle() != null && (!bookCreate.getTitle().equals(book.getTitle()))) {
      book.setTitle(bookCreate.getTitle());
      update = true;
    }

    if (bookCreate.getDeleted() != null && (!bookCreate.getDeleted().equals(book.isDeleted()))) {
      book.setDeleted(bookCreate.getDeleted());
      update = true;
    }

    return update;
  }

  /**
   * @param bookUpdate
   * @param securityContext
   * @return book
   */
  public Book updateBook(BookUpdate bookUpdate, UserSecurityContext securityContext) {

    Book book = bookUpdate.getBook();

    if (updateBookNoMerge(book, bookUpdate)) {
      book = this.repository.merge(book);
    }

    return book;
  }

  /**
   * @param bookFilter Object Used to List Book
   * @param securityContext
   * @return PaginationResponse<Book> containing paging information for Book
   */
  public PaginationResponse<Book> getAllBooks(
      BookFilter bookFilter, UserSecurityContext securityContext) {

    List<Book> list = listAllBooks(bookFilter, securityContext);
    long count = this.repository.countAllBooks(bookFilter, securityContext);

    return new PaginationResponse<>(list, bookFilter.getPageSize(), count);
  }

  /**
   * @param bookFilter Object Used to List Book
   * @param securityContext
   * @return List of Book
   */
  public List<Book> listAllBooks(BookFilter bookFilter, UserSecurityContext securityContext) {

    return this.repository.listAllBooks(bookFilter, securityContext);
  }

  public Book deleteBook(String id, UserSecurityContext securityContext) {

    Book book = this.repository.getByIdOrNull(Book.class, Book_.id, id, securityContext);

    if (book == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book not found");
    }

    this.repository.remove(book);

    return book;
  }

  public <T extends Book, I> List<T> listByIds(
      Class<T> c,
      SingularAttribute<? super T, I> idField,
      Set<I> ids,
      UserSecurityContext securityContext) {
    return repository.listByIds(c, idField, ids, securityContext);
  }

  public <T extends Book, I> T getByIdOrNull(
      Class<T> c,
      SingularAttribute<? super T, I> idField,
      I id,
      UserSecurityContext securityContext) {
    return repository.getByIdOrNull(c, idField, id, securityContext);
  }

  public <T extends Book, I> T getByIdOrNull(
      Class<T> c, SingularAttribute<? super T, I> idField, I id) {
    return repository.getByIdOrNull(c, idField, id);
  }

  public <T extends Book, I> List<T> listByIds(
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

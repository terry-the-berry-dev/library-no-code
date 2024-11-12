package com.lighthouse.library.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lighthouse.library.model.model.Author;
import com.lighthouse.library.model.model.Book;
import com.lighthouse.library.model.validation.IdValid;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.Set;

/** Object Used to List AuthorToBook */
@IdValid.List({
  @IdValid(
      field = "authorIds",
      fieldType = Author.class,
      targetField = "authors",
      groups = {}),
  @IdValid(
      field = "bookIds",
      fieldType = Book.class,
      targetField = "books",
      groups = {})
})
public class AuthorToBookFilter {

  private Set<String> authorIds;

  @JsonIgnore private List<Author> authors;

  private Set<String> bookIds;

  @JsonIgnore private List<Book> books;

  @Min(
      value = 0,
      groups = {})
  private Integer currentPage;

  private Set<String> id;

  @Min(
      value = 1,
      groups = {})
  private Integer pageSize;

  public AuthorToBookFilter() {}

  /**
   * @return authorIds
   */
  public Set<String> getAuthorIds() {
    return this.authorIds;
  }

  /**
   * @param authorIds to set
   * @return AuthorToBookFilter
   */
  public <T extends AuthorToBookFilter> T setAuthorIds(Set<String> authorIds) {
    this.authorIds = authorIds;
    return (T) this;
  }

  /**
   * @return authors
   */
  @JsonIgnore
  public List<Author> getAuthors() {
    return this.authors;
  }

  /**
   * @param authors to set
   * @return AuthorToBookFilter
   */
  public <T extends AuthorToBookFilter> T setAuthors(List<Author> authors) {
    this.authors = authors;
    return (T) this;
  }

  /**
   * @return bookIds
   */
  public Set<String> getBookIds() {
    return this.bookIds;
  }

  /**
   * @param bookIds to set
   * @return AuthorToBookFilter
   */
  public <T extends AuthorToBookFilter> T setBookIds(Set<String> bookIds) {
    this.bookIds = bookIds;
    return (T) this;
  }

  /**
   * @return books
   */
  @JsonIgnore
  public List<Book> getBooks() {
    return this.books;
  }

  /**
   * @param books to set
   * @return AuthorToBookFilter
   */
  public <T extends AuthorToBookFilter> T setBooks(List<Book> books) {
    this.books = books;
    return (T) this;
  }

  /**
   * @return currentPage
   */
  public Integer getCurrentPage() {
    return this.currentPage;
  }

  /**
   * @param currentPage to set
   * @return AuthorToBookFilter
   */
  public <T extends AuthorToBookFilter> T setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
    return (T) this;
  }

  /**
   * @return id
   */
  public Set<String> getId() {
    return this.id;
  }

  /**
   * @param id to set
   * @return AuthorToBookFilter
   */
  public <T extends AuthorToBookFilter> T setId(Set<String> id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return pageSize
   */
  public Integer getPageSize() {
    return this.pageSize;
  }

  /**
   * @param pageSize to set
   * @return AuthorToBookFilter
   */
  public <T extends AuthorToBookFilter> T setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return (T) this;
  }
}

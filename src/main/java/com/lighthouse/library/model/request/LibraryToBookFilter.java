package com.lighthouse.library.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lighthouse.library.model.model.Book;
import com.lighthouse.library.model.model.Library;
import com.lighthouse.library.model.validation.IdValid;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.Set;

/** Object Used to List LibraryToBook */
@IdValid.List({
  @IdValid(
      field = "libraryIds",
      fieldType = Library.class,
      targetField = "libraries",
      groups = {}),
  @IdValid(
      field = "bookIds",
      fieldType = Book.class,
      targetField = "books",
      groups = {})
})
public class LibraryToBookFilter {

  private Set<String> bookIds;

  @JsonIgnore private List<Book> books;

  @Min(
      value = 0,
      groups = {})
  private Integer currentPage;

  private Set<String> id;

  @JsonIgnore private List<Library> libraries;

  private Set<String> libraryIds;

  @Min(
      value = 1,
      groups = {})
  private Integer pageSize;

  public LibraryToBookFilter() {}

  /**
   * @return bookIds
   */
  public Set<String> getBookIds() {
    return this.bookIds;
  }

  /**
   * @param bookIds to set
   * @return LibraryToBookFilter
   */
  public <T extends LibraryToBookFilter> T setBookIds(Set<String> bookIds) {
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
   * @return LibraryToBookFilter
   */
  public <T extends LibraryToBookFilter> T setBooks(List<Book> books) {
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
   * @return LibraryToBookFilter
   */
  public <T extends LibraryToBookFilter> T setCurrentPage(Integer currentPage) {
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
   * @return LibraryToBookFilter
   */
  public <T extends LibraryToBookFilter> T setId(Set<String> id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return libraries
   */
  @JsonIgnore
  public List<Library> getLibraries() {
    return this.libraries;
  }

  /**
   * @param libraries to set
   * @return LibraryToBookFilter
   */
  public <T extends LibraryToBookFilter> T setLibraries(List<Library> libraries) {
    this.libraries = libraries;
    return (T) this;
  }

  /**
   * @return libraryIds
   */
  public Set<String> getLibraryIds() {
    return this.libraryIds;
  }

  /**
   * @param libraryIds to set
   * @return LibraryToBookFilter
   */
  public <T extends LibraryToBookFilter> T setLibraryIds(Set<String> libraryIds) {
    this.libraryIds = libraryIds;
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
   * @return LibraryToBookFilter
   */
  public <T extends LibraryToBookFilter> T setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return (T) this;
  }
}

package com.lighthouse.library.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lighthouse.library.model.model.AuthorToBook;
import com.lighthouse.library.model.model.BookToGenre;
import com.lighthouse.library.model.model.LenderToBook;
import com.lighthouse.library.model.model.LibraryToBook;
import com.lighthouse.library.model.validation.IdValid;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.Set;

/** Object Used to List Book */
@IdValid.List({
  @IdValid(
      field = "bookLenderToBooksIds",
      fieldType = LenderToBook.class,
      targetField = "bookLenderToBookses",
      groups = {}),
  @IdValid(
      field = "bookAuthorToBooksIds",
      fieldType = AuthorToBook.class,
      targetField = "bookAuthorToBookses",
      groups = {}),
  @IdValid(
      field = "bookLibraryToBooksIds",
      fieldType = LibraryToBook.class,
      targetField = "bookLibraryToBookses",
      groups = {}),
  @IdValid(
      field = "bookBookToGenresIds",
      fieldType = BookToGenre.class,
      targetField = "bookBookToGenreses",
      groups = {})
})
public class BookFilter {

  private Set<String> bookAuthorToBooksIds;

  @JsonIgnore private List<AuthorToBook> bookAuthorToBookses;

  private Set<String> bookBookToGenresIds;

  @JsonIgnore private List<BookToGenre> bookBookToGenreses;

  private Set<String> bookLenderToBooksIds;

  @JsonIgnore private List<LenderToBook> bookLenderToBookses;

  private Set<String> bookLibraryToBooksIds;

  @JsonIgnore private List<LibraryToBook> bookLibraryToBookses;

  @Min(
      value = 0,
      groups = {})
  private Integer currentPage;

  private Set<Boolean> deleted;

  private Set<String> id;

  @Min(
      value = 1,
      groups = {})
  private Integer pageSize;

  private Set<String> title;

  private String titleLike;

  public BookFilter() {}

  /**
   * @return bookAuthorToBooksIds
   */
  public Set<String> getBookAuthorToBooksIds() {
    return this.bookAuthorToBooksIds;
  }

  /**
   * @param bookAuthorToBooksIds to set
   * @return BookFilter
   */
  public <T extends BookFilter> T setBookAuthorToBooksIds(Set<String> bookAuthorToBooksIds) {
    this.bookAuthorToBooksIds = bookAuthorToBooksIds;
    return (T) this;
  }

  /**
   * @return bookAuthorToBookses
   */
  @JsonIgnore
  public List<AuthorToBook> getBookAuthorToBookses() {
    return this.bookAuthorToBookses;
  }

  /**
   * @param bookAuthorToBookses to set
   * @return BookFilter
   */
  public <T extends BookFilter> T setBookAuthorToBookses(List<AuthorToBook> bookAuthorToBookses) {
    this.bookAuthorToBookses = bookAuthorToBookses;
    return (T) this;
  }

  /**
   * @return bookBookToGenresIds
   */
  public Set<String> getBookBookToGenresIds() {
    return this.bookBookToGenresIds;
  }

  /**
   * @param bookBookToGenresIds to set
   * @return BookFilter
   */
  public <T extends BookFilter> T setBookBookToGenresIds(Set<String> bookBookToGenresIds) {
    this.bookBookToGenresIds = bookBookToGenresIds;
    return (T) this;
  }

  /**
   * @return bookBookToGenreses
   */
  @JsonIgnore
  public List<BookToGenre> getBookBookToGenreses() {
    return this.bookBookToGenreses;
  }

  /**
   * @param bookBookToGenreses to set
   * @return BookFilter
   */
  public <T extends BookFilter> T setBookBookToGenreses(List<BookToGenre> bookBookToGenreses) {
    this.bookBookToGenreses = bookBookToGenreses;
    return (T) this;
  }

  /**
   * @return bookLenderToBooksIds
   */
  public Set<String> getBookLenderToBooksIds() {
    return this.bookLenderToBooksIds;
  }

  /**
   * @param bookLenderToBooksIds to set
   * @return BookFilter
   */
  public <T extends BookFilter> T setBookLenderToBooksIds(Set<String> bookLenderToBooksIds) {
    this.bookLenderToBooksIds = bookLenderToBooksIds;
    return (T) this;
  }

  /**
   * @return bookLenderToBookses
   */
  @JsonIgnore
  public List<LenderToBook> getBookLenderToBookses() {
    return this.bookLenderToBookses;
  }

  /**
   * @param bookLenderToBookses to set
   * @return BookFilter
   */
  public <T extends BookFilter> T setBookLenderToBookses(List<LenderToBook> bookLenderToBookses) {
    this.bookLenderToBookses = bookLenderToBookses;
    return (T) this;
  }

  /**
   * @return bookLibraryToBooksIds
   */
  public Set<String> getBookLibraryToBooksIds() {
    return this.bookLibraryToBooksIds;
  }

  /**
   * @param bookLibraryToBooksIds to set
   * @return BookFilter
   */
  public <T extends BookFilter> T setBookLibraryToBooksIds(Set<String> bookLibraryToBooksIds) {
    this.bookLibraryToBooksIds = bookLibraryToBooksIds;
    return (T) this;
  }

  /**
   * @return bookLibraryToBookses
   */
  @JsonIgnore
  public List<LibraryToBook> getBookLibraryToBookses() {
    return this.bookLibraryToBookses;
  }

  /**
   * @param bookLibraryToBookses to set
   * @return BookFilter
   */
  public <T extends BookFilter> T setBookLibraryToBookses(
      List<LibraryToBook> bookLibraryToBookses) {
    this.bookLibraryToBookses = bookLibraryToBookses;
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
   * @return BookFilter
   */
  public <T extends BookFilter> T setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
    return (T) this;
  }

  /**
   * @return deleted
   */
  public Set<Boolean> getDeleted() {
    return this.deleted;
  }

  /**
   * @param deleted to set
   * @return BookFilter
   */
  public <T extends BookFilter> T setDeleted(Set<Boolean> deleted) {
    this.deleted = deleted;
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
   * @return BookFilter
   */
  public <T extends BookFilter> T setId(Set<String> id) {
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
   * @return BookFilter
   */
  public <T extends BookFilter> T setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return (T) this;
  }

  /**
   * @return title
   */
  public Set<String> getTitle() {
    return this.title;
  }

  /**
   * @param title to set
   * @return BookFilter
   */
  public <T extends BookFilter> T setTitle(Set<String> title) {
    this.title = title;
    return (T) this;
  }

  /**
   * @return titleLike
   */
  public String getTitleLike() {
    return this.titleLike;
  }

  /**
   * @param titleLike to set
   * @return BookFilter
   */
  public <T extends BookFilter> T setTitleLike(String titleLike) {
    this.titleLike = titleLike;
    return (T) this;
  }
}

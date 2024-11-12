package com.lighthouse.library.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lighthouse.library.model.model.Book;
import com.lighthouse.library.model.model.Genre;
import com.lighthouse.library.model.validation.IdValid;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.Set;

/** Object Used to List BookToGenre */
@IdValid.List({
  @IdValid(
      field = "genreIds",
      fieldType = Genre.class,
      targetField = "genres",
      groups = {}),
  @IdValid(
      field = "bookIds",
      fieldType = Book.class,
      targetField = "books",
      groups = {})
})
public class BookToGenreFilter {

  private Set<String> bookIds;

  @JsonIgnore private List<Book> books;

  @Min(
      value = 0,
      groups = {})
  private Integer currentPage;

  private Set<String> genreIds;

  @JsonIgnore private List<Genre> genres;

  private Set<String> id;

  @Min(
      value = 1,
      groups = {})
  private Integer pageSize;

  public BookToGenreFilter() {}

  /**
   * @return bookIds
   */
  public Set<String> getBookIds() {
    return this.bookIds;
  }

  /**
   * @param bookIds to set
   * @return BookToGenreFilter
   */
  public <T extends BookToGenreFilter> T setBookIds(Set<String> bookIds) {
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
   * @return BookToGenreFilter
   */
  public <T extends BookToGenreFilter> T setBooks(List<Book> books) {
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
   * @return BookToGenreFilter
   */
  public <T extends BookToGenreFilter> T setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
    return (T) this;
  }

  /**
   * @return genreIds
   */
  public Set<String> getGenreIds() {
    return this.genreIds;
  }

  /**
   * @param genreIds to set
   * @return BookToGenreFilter
   */
  public <T extends BookToGenreFilter> T setGenreIds(Set<String> genreIds) {
    this.genreIds = genreIds;
    return (T) this;
  }

  /**
   * @return genres
   */
  @JsonIgnore
  public List<Genre> getGenres() {
    return this.genres;
  }

  /**
   * @param genres to set
   * @return BookToGenreFilter
   */
  public <T extends BookToGenreFilter> T setGenres(List<Genre> genres) {
    this.genres = genres;
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
   * @return BookToGenreFilter
   */
  public <T extends BookToGenreFilter> T setId(Set<String> id) {
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
   * @return BookToGenreFilter
   */
  public <T extends BookToGenreFilter> T setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return (T) this;
  }
}

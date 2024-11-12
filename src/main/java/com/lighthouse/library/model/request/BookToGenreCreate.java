package com.lighthouse.library.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lighthouse.library.model.model.Book;
import com.lighthouse.library.model.model.Genre;
import com.lighthouse.library.model.validation.Create;
import com.lighthouse.library.model.validation.IdValid;
import com.lighthouse.library.model.validation.Update;

/** Object Used to Create BookToGenre */
@IdValid.List({
  @IdValid(
      field = "genreId",
      fieldType = Genre.class,
      targetField = "genre",
      groups = {Update.class, Create.class}),
  @IdValid(
      field = "bookId",
      fieldType = Book.class,
      targetField = "book",
      groups = {Update.class, Create.class})
})
public class BookToGenreCreate {

  @JsonIgnore private Book book;

  private String bookId;

  @JsonIgnore private Genre genre;

  private String genreId;

  public BookToGenreCreate() {}

  /**
   * @return book
   */
  @JsonIgnore
  public Book getBook() {
    return this.book;
  }

  /**
   * @param book to set
   * @return BookToGenreCreate
   */
  public <T extends BookToGenreCreate> T setBook(Book book) {
    this.book = book;
    return (T) this;
  }

  /**
   * @return bookId
   */
  public String getBookId() {
    return this.bookId;
  }

  /**
   * @param bookId to set
   * @return BookToGenreCreate
   */
  public <T extends BookToGenreCreate> T setBookId(String bookId) {
    this.bookId = bookId;
    return (T) this;
  }

  /**
   * @return genre
   */
  @JsonIgnore
  public Genre getGenre() {
    return this.genre;
  }

  /**
   * @param genre to set
   * @return BookToGenreCreate
   */
  public <T extends BookToGenreCreate> T setGenre(Genre genre) {
    this.genre = genre;
    return (T) this;
  }

  /**
   * @return genreId
   */
  public String getGenreId() {
    return this.genreId;
  }

  /**
   * @param genreId to set
   * @return BookToGenreCreate
   */
  public <T extends BookToGenreCreate> T setGenreId(String genreId) {
    this.genreId = genreId;
    return (T) this;
  }
}

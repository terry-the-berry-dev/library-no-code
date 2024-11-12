package com.lighthouse.library.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lighthouse.library.model.model.Book;
import com.lighthouse.library.model.model.Library;
import com.lighthouse.library.model.validation.Create;
import com.lighthouse.library.model.validation.IdValid;
import com.lighthouse.library.model.validation.Update;

/** Object Used to Create LibraryToBook */
@IdValid.List({
  @IdValid(
      field = "libraryId",
      fieldType = Library.class,
      targetField = "library",
      groups = {Update.class, Create.class}),
  @IdValid(
      field = "bookId",
      fieldType = Book.class,
      targetField = "book",
      groups = {Update.class, Create.class})
})
public class LibraryToBookCreate {

  @JsonIgnore private Book book;

  private String bookId;

  @JsonIgnore private Library library;

  private String libraryId;

  public LibraryToBookCreate() {}

  /**
   * @return book
   */
  @JsonIgnore
  public Book getBook() {
    return this.book;
  }

  /**
   * @param book to set
   * @return LibraryToBookCreate
   */
  public <T extends LibraryToBookCreate> T setBook(Book book) {
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
   * @return LibraryToBookCreate
   */
  public <T extends LibraryToBookCreate> T setBookId(String bookId) {
    this.bookId = bookId;
    return (T) this;
  }

  /**
   * @return library
   */
  @JsonIgnore
  public Library getLibrary() {
    return this.library;
  }

  /**
   * @param library to set
   * @return LibraryToBookCreate
   */
  public <T extends LibraryToBookCreate> T setLibrary(Library library) {
    this.library = library;
    return (T) this;
  }

  /**
   * @return libraryId
   */
  public String getLibraryId() {
    return this.libraryId;
  }

  /**
   * @param libraryId to set
   * @return LibraryToBookCreate
   */
  public <T extends LibraryToBookCreate> T setLibraryId(String libraryId) {
    this.libraryId = libraryId;
    return (T) this;
  }
}

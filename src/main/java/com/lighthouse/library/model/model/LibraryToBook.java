package com.lighthouse.library.model.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class LibraryToBook {

  @Id private String id;

  @ManyToOne(targetEntity = Library.class)
  private Library library;

  @ManyToOne(targetEntity = Book.class)
  private Book book;

  public LibraryToBook() {}

  /**
   * @return id
   */
  @Id
  public String getId() {
    return this.id;
  }

  /**
   * @param id to set
   * @return LibraryToBook
   */
  public <T extends LibraryToBook> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return library
   */
  @ManyToOne(targetEntity = Library.class)
  public Library getLibrary() {
    return this.library;
  }

  /**
   * @param library to set
   * @return LibraryToBook
   */
  public <T extends LibraryToBook> T setLibrary(Library library) {
    this.library = library;
    return (T) this;
  }

  /**
   * @return book
   */
  @ManyToOne(targetEntity = Book.class)
  public Book getBook() {
    return this.book;
  }

  /**
   * @param book to set
   * @return LibraryToBook
   */
  public <T extends LibraryToBook> T setBook(Book book) {
    this.book = book;
    return (T) this;
  }
}

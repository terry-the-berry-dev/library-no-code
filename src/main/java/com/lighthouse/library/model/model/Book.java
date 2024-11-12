package com.lighthouse.library.model.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Book {

  @OneToMany(targetEntity = AuthorToBook.class)
  @JsonIgnore
  private List<AuthorToBook> bookAuthorToBooks;

  @OneToMany(targetEntity = BookToGenre.class)
  @JsonIgnore
  private List<BookToGenre> bookBookToGenres;

  @OneToMany(targetEntity = LenderToBook.class)
  @JsonIgnore
  private List<LenderToBook> bookLenderToBooks;

  @OneToMany(targetEntity = LibraryToBook.class)
  @JsonIgnore
  private List<LibraryToBook> bookLibraryToBooks;

  @Id private String id;

  private String title;

  private boolean deleted;

  public Book() {}

  /**
   * @return bookAuthorToBooks
   */
  @OneToMany(targetEntity = AuthorToBook.class)
  @JsonIgnore
  public List<AuthorToBook> getBookAuthorToBooks() {
    return this.bookAuthorToBooks;
  }

  /**
   * @param bookAuthorToBooks to set
   * @return Book
   */
  public <T extends Book> T setBookAuthorToBooks(List<AuthorToBook> bookAuthorToBooks) {
    this.bookAuthorToBooks = bookAuthorToBooks;
    return (T) this;
  }

  /**
   * @return bookBookToGenres
   */
  @OneToMany(targetEntity = BookToGenre.class)
  @JsonIgnore
  public List<BookToGenre> getBookBookToGenres() {
    return this.bookBookToGenres;
  }

  /**
   * @param bookBookToGenres to set
   * @return Book
   */
  public <T extends Book> T setBookBookToGenres(List<BookToGenre> bookBookToGenres) {
    this.bookBookToGenres = bookBookToGenres;
    return (T) this;
  }

  /**
   * @return bookLenderToBooks
   */
  @OneToMany(targetEntity = LenderToBook.class)
  @JsonIgnore
  public List<LenderToBook> getBookLenderToBooks() {
    return this.bookLenderToBooks;
  }

  /**
   * @param bookLenderToBooks to set
   * @return Book
   */
  public <T extends Book> T setBookLenderToBooks(List<LenderToBook> bookLenderToBooks) {
    this.bookLenderToBooks = bookLenderToBooks;
    return (T) this;
  }

  /**
   * @return bookLibraryToBooks
   */
  @OneToMany(targetEntity = LibraryToBook.class)
  @JsonIgnore
  public List<LibraryToBook> getBookLibraryToBooks() {
    return this.bookLibraryToBooks;
  }

  /**
   * @param bookLibraryToBooks to set
   * @return Book
   */
  public <T extends Book> T setBookLibraryToBooks(List<LibraryToBook> bookLibraryToBooks) {
    this.bookLibraryToBooks = bookLibraryToBooks;
    return (T) this;
  }

  /**
   * @return id
   */
  @Id
  public String getId() {
    return this.id;
  }

  /**
   * @param id to set
   * @return Book
   */
  public <T extends Book> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return title
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * @param title to set
   * @return Book
   */
  public <T extends Book> T setTitle(String title) {
    this.title = title;
    return (T) this;
  }

  /**
   * @return deleted
   */
  public boolean isDeleted() {
    return this.deleted;
  }

  /**
   * @param deleted to set
   * @return Book
   */
  public <T extends Book> T setDeleted(boolean deleted) {
    this.deleted = deleted;
    return (T) this;
  }
}

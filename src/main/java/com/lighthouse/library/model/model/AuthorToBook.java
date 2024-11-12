package com.lighthouse.library.model.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class AuthorToBook {

  @Id private String id;

  @ManyToOne(targetEntity = Author.class)
  private Author author;

  @ManyToOne(targetEntity = Book.class)
  private Book book;

  public AuthorToBook() {}

  /**
   * @return id
   */
  @Id
  public String getId() {
    return this.id;
  }

  /**
   * @param id to set
   * @return AuthorToBook
   */
  public <T extends AuthorToBook> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return author
   */
  @ManyToOne(targetEntity = Author.class)
  public Author getAuthor() {
    return this.author;
  }

  /**
   * @param author to set
   * @return AuthorToBook
   */
  public <T extends AuthorToBook> T setAuthor(Author author) {
    this.author = author;
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
   * @return AuthorToBook
   */
  public <T extends AuthorToBook> T setBook(Book book) {
    this.book = book;
    return (T) this;
  }
}

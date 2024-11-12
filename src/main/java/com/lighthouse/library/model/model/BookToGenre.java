package com.lighthouse.library.model.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class BookToGenre {

  @Id private String id;

  @ManyToOne(targetEntity = Book.class)
  private Book book;

  @ManyToOne(targetEntity = Genre.class)
  private Genre genre;

  public BookToGenre() {}

  /**
   * @return id
   */
  @Id
  public String getId() {
    return this.id;
  }

  /**
   * @param id to set
   * @return BookToGenre
   */
  public <T extends BookToGenre> T setId(String id) {
    this.id = id;
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
   * @return BookToGenre
   */
  public <T extends BookToGenre> T setBook(Book book) {
    this.book = book;
    return (T) this;
  }

  /**
   * @return genre
   */
  @ManyToOne(targetEntity = Genre.class)
  public Genre getGenre() {
    return this.genre;
  }

  /**
   * @param genre to set
   * @return BookToGenre
   */
  public <T extends BookToGenre> T setGenre(Genre genre) {
    this.genre = genre;
    return (T) this;
  }
}

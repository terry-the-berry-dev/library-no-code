package com.lighthouse.library.model.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class LenderToBook {

  @Id private String id;

  @ManyToOne(targetEntity = Lender.class)
  private Lender lender;

  @ManyToOne(targetEntity = Book.class)
  private Book book;

  public LenderToBook() {}

  /**
   * @return id
   */
  @Id
  public String getId() {
    return this.id;
  }

  /**
   * @param id to set
   * @return LenderToBook
   */
  public <T extends LenderToBook> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return lender
   */
  @ManyToOne(targetEntity = Lender.class)
  public Lender getLender() {
    return this.lender;
  }

  /**
   * @param lender to set
   * @return LenderToBook
   */
  public <T extends LenderToBook> T setLender(Lender lender) {
    this.lender = lender;
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
   * @return LenderToBook
   */
  public <T extends LenderToBook> T setBook(Book book) {
    this.book = book;
    return (T) this;
  }
}

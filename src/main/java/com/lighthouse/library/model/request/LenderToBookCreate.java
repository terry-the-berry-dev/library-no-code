package com.lighthouse.library.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lighthouse.library.model.model.Book;
import com.lighthouse.library.model.model.Lender;
import com.lighthouse.library.model.validation.Create;
import com.lighthouse.library.model.validation.IdValid;
import com.lighthouse.library.model.validation.Update;

/** Object Used to Create LenderToBook */
@IdValid.List({
  @IdValid(
      field = "lenderId",
      fieldType = Lender.class,
      targetField = "lender",
      groups = {Update.class, Create.class}),
  @IdValid(
      field = "bookId",
      fieldType = Book.class,
      targetField = "book",
      groups = {Update.class, Create.class})
})
public class LenderToBookCreate {

  @JsonIgnore private Book book;

  private String bookId;

  @JsonIgnore private Lender lender;

  private String lenderId;

  public LenderToBookCreate() {}

  /**
   * @return book
   */
  @JsonIgnore
  public Book getBook() {
    return this.book;
  }

  /**
   * @param book to set
   * @return LenderToBookCreate
   */
  public <T extends LenderToBookCreate> T setBook(Book book) {
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
   * @return LenderToBookCreate
   */
  public <T extends LenderToBookCreate> T setBookId(String bookId) {
    this.bookId = bookId;
    return (T) this;
  }

  /**
   * @return lender
   */
  @JsonIgnore
  public Lender getLender() {
    return this.lender;
  }

  /**
   * @param lender to set
   * @return LenderToBookCreate
   */
  public <T extends LenderToBookCreate> T setLender(Lender lender) {
    this.lender = lender;
    return (T) this;
  }

  /**
   * @return lenderId
   */
  public String getLenderId() {
    return this.lenderId;
  }

  /**
   * @param lenderId to set
   * @return LenderToBookCreate
   */
  public <T extends LenderToBookCreate> T setLenderId(String lenderId) {
    this.lenderId = lenderId;
    return (T) this;
  }
}

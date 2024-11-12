package com.lighthouse.library.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lighthouse.library.model.model.Book;
import com.lighthouse.library.model.model.Lender;
import com.lighthouse.library.model.validation.IdValid;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.Set;

/** Object Used to List LenderToBook */
@IdValid.List({
  @IdValid(
      field = "lenderIds",
      fieldType = Lender.class,
      targetField = "lenders",
      groups = {}),
  @IdValid(
      field = "bookIds",
      fieldType = Book.class,
      targetField = "books",
      groups = {})
})
public class LenderToBookFilter {

  private Set<String> bookIds;

  @JsonIgnore private List<Book> books;

  @Min(
      value = 0,
      groups = {})
  private Integer currentPage;

  private Set<String> id;

  private Set<String> lenderIds;

  @JsonIgnore private List<Lender> lenders;

  @Min(
      value = 1,
      groups = {})
  private Integer pageSize;

  public LenderToBookFilter() {}

  /**
   * @return bookIds
   */
  public Set<String> getBookIds() {
    return this.bookIds;
  }

  /**
   * @param bookIds to set
   * @return LenderToBookFilter
   */
  public <T extends LenderToBookFilter> T setBookIds(Set<String> bookIds) {
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
   * @return LenderToBookFilter
   */
  public <T extends LenderToBookFilter> T setBooks(List<Book> books) {
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
   * @return LenderToBookFilter
   */
  public <T extends LenderToBookFilter> T setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
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
   * @return LenderToBookFilter
   */
  public <T extends LenderToBookFilter> T setId(Set<String> id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return lenderIds
   */
  public Set<String> getLenderIds() {
    return this.lenderIds;
  }

  /**
   * @param lenderIds to set
   * @return LenderToBookFilter
   */
  public <T extends LenderToBookFilter> T setLenderIds(Set<String> lenderIds) {
    this.lenderIds = lenderIds;
    return (T) this;
  }

  /**
   * @return lenders
   */
  @JsonIgnore
  public List<Lender> getLenders() {
    return this.lenders;
  }

  /**
   * @param lenders to set
   * @return LenderToBookFilter
   */
  public <T extends LenderToBookFilter> T setLenders(List<Lender> lenders) {
    this.lenders = lenders;
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
   * @return LenderToBookFilter
   */
  public <T extends LenderToBookFilter> T setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return (T) this;
  }
}

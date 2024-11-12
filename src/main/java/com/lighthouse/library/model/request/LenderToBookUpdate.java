package com.lighthouse.library.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lighthouse.library.model.model.LenderToBook;
import com.lighthouse.library.model.validation.IdValid;
import com.lighthouse.library.model.validation.Update;

/** Object Used to Update LenderToBook */
@IdValid.List({
  @IdValid(
      field = "id",
      fieldType = LenderToBook.class,
      targetField = "lenderToBook",
      groups = {Update.class})
})
public class LenderToBookUpdate extends LenderToBookCreate {

  private String id;

  @JsonIgnore private LenderToBook lenderToBook;

  public LenderToBookUpdate() {}

  /**
   * @return id
   */
  public String getId() {
    return this.id;
  }

  /**
   * @param id to set
   * @return LenderToBookUpdate
   */
  public <T extends LenderToBookUpdate> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return lenderToBook
   */
  @JsonIgnore
  public LenderToBook getLenderToBook() {
    return this.lenderToBook;
  }

  /**
   * @param lenderToBook to set
   * @return LenderToBookUpdate
   */
  public <T extends LenderToBookUpdate> T setLenderToBook(LenderToBook lenderToBook) {
    this.lenderToBook = lenderToBook;
    return (T) this;
  }
}

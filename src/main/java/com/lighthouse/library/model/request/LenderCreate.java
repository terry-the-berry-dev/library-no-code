package com.lighthouse.library.model.request;

/** Object Used to Create Lender */
public class LenderCreate {

  private Boolean deleted;

  private String name;

  public LenderCreate() {}

  /**
   * @return deleted
   */
  public Boolean getDeleted() {
    return this.deleted;
  }

  /**
   * @param deleted to set
   * @return LenderCreate
   */
  public <T extends LenderCreate> T setDeleted(Boolean deleted) {
    this.deleted = deleted;
    return (T) this;
  }

  /**
   * @return name
   */
  public String getName() {
    return this.name;
  }

  /**
   * @param name to set
   * @return LenderCreate
   */
  public <T extends LenderCreate> T setName(String name) {
    this.name = name;
    return (T) this;
  }
}

package com.lighthouse.library.model.request;

/** Object Used to Create Book */
public class BookCreate {

  private Boolean deleted;

  private String title;

  public BookCreate() {}

  /**
   * @return deleted
   */
  public Boolean getDeleted() {
    return this.deleted;
  }

  /**
   * @param deleted to set
   * @return BookCreate
   */
  public <T extends BookCreate> T setDeleted(Boolean deleted) {
    this.deleted = deleted;
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
   * @return BookCreate
   */
  public <T extends BookCreate> T setTitle(String title) {
    this.title = title;
    return (T) this;
  }
}

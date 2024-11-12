package com.lighthouse.library.model.request;

/** Object Used to Create Genre */
public class GenreCreate {

  private Boolean deleted;

  private String name;

  public GenreCreate() {}

  /**
   * @return deleted
   */
  public Boolean getDeleted() {
    return this.deleted;
  }

  /**
   * @param deleted to set
   * @return GenreCreate
   */
  public <T extends GenreCreate> T setDeleted(Boolean deleted) {
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
   * @return GenreCreate
   */
  public <T extends GenreCreate> T setName(String name) {
    this.name = name;
    return (T) this;
  }
}

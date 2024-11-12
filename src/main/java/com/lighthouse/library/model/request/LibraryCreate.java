package com.lighthouse.library.model.request;

/** Object Used to Create Library */
public class LibraryCreate {

  private Boolean deleted;

  private String name;

  public LibraryCreate() {}

  /**
   * @return deleted
   */
  public Boolean getDeleted() {
    return this.deleted;
  }

  /**
   * @param deleted to set
   * @return LibraryCreate
   */
  public <T extends LibraryCreate> T setDeleted(Boolean deleted) {
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
   * @return LibraryCreate
   */
  public <T extends LibraryCreate> T setName(String name) {
    this.name = name;
    return (T) this;
  }
}

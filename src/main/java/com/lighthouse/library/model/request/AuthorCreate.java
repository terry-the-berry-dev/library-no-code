package com.lighthouse.library.model.request;

/** Object Used to Create Author */
public class AuthorCreate {

  private String deleted;

  private String name;

  public AuthorCreate() {}

  /**
   * @return deleted
   */
  public String getDeleted() {
    return this.deleted;
  }

  /**
   * @param deleted to set
   * @return AuthorCreate
   */
  public <T extends AuthorCreate> T setDeleted(String deleted) {
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
   * @return AuthorCreate
   */
  public <T extends AuthorCreate> T setName(String name) {
    this.name = name;
    return (T) this;
  }
}

package com.lighthouse.library.model.request;

/** Object Used to Create AppUser */
public class AppUserCreate {

  private String password;

  private String roles;

  private String username;

  public AppUserCreate() {}

  /**
   * @return password
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * @param password to set
   * @return AppUserCreate
   */
  public <T extends AppUserCreate> T setPassword(String password) {
    this.password = password;
    return (T) this;
  }

  /**
   * @return roles
   */
  public String getRoles() {
    return this.roles;
  }

  /**
   * @param roles to set
   * @return AppUserCreate
   */
  public <T extends AppUserCreate> T setRoles(String roles) {
    this.roles = roles;
    return (T) this;
  }

  /**
   * @return username
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * @param username to set
   * @return AppUserCreate
   */
  public <T extends AppUserCreate> T setUsername(String username) {
    this.username = username;
    return (T) this;
  }
}

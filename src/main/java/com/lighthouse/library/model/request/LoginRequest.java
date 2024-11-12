package com.lighthouse.library.model.request;

public class LoginRequest {

  private String password;

  private String username;

  public LoginRequest() {}

  /**
   * @return password
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * @param password to set
   * @return LoginRequest
   */
  public <T extends LoginRequest> T setPassword(String password) {
    this.password = password;
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
   * @return LoginRequest
   */
  public <T extends LoginRequest> T setUsername(String username) {
    this.username = username;
    return (T) this;
  }
}

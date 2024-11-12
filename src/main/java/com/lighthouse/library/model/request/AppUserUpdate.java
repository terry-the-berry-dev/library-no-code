package com.lighthouse.library.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lighthouse.library.model.model.AppUser;
import com.lighthouse.library.model.validation.IdValid;
import com.lighthouse.library.model.validation.Update;

/** Object Used to Update AppUser */
@IdValid.List({
  @IdValid(
      field = "id",
      fieldType = AppUser.class,
      targetField = "appUser",
      groups = {Update.class})
})
public class AppUserUpdate extends AppUserCreate {

  @JsonIgnore private AppUser appUser;

  private String id;

  public AppUserUpdate() {}

  /**
   * @return appUser
   */
  @JsonIgnore
  public AppUser getAppUser() {
    return this.appUser;
  }

  /**
   * @param appUser to set
   * @return AppUserUpdate
   */
  public <T extends AppUserUpdate> T setAppUser(AppUser appUser) {
    this.appUser = appUser;
    return (T) this;
  }

  /**
   * @return id
   */
  public String getId() {
    return this.id;
  }

  /**
   * @param id to set
   * @return AppUserUpdate
   */
  public <T extends AppUserUpdate> T setId(String id) {
    this.id = id;
    return (T) this;
  }
}

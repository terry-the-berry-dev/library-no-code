package com.lighthouse.library.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lighthouse.library.model.model.LibraryToLender;
import com.lighthouse.library.model.validation.IdValid;
import com.lighthouse.library.model.validation.Update;

/** Object Used to Update LibraryToLender */
@IdValid.List({
  @IdValid(
      field = "id",
      fieldType = LibraryToLender.class,
      targetField = "libraryToLender",
      groups = {Update.class})
})
public class LibraryToLenderUpdate extends LibraryToLenderCreate {

  private String id;

  @JsonIgnore private LibraryToLender libraryToLender;

  public LibraryToLenderUpdate() {}

  /**
   * @return id
   */
  public String getId() {
    return this.id;
  }

  /**
   * @param id to set
   * @return LibraryToLenderUpdate
   */
  public <T extends LibraryToLenderUpdate> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return libraryToLender
   */
  @JsonIgnore
  public LibraryToLender getLibraryToLender() {
    return this.libraryToLender;
  }

  /**
   * @param libraryToLender to set
   * @return LibraryToLenderUpdate
   */
  public <T extends LibraryToLenderUpdate> T setLibraryToLender(LibraryToLender libraryToLender) {
    this.libraryToLender = libraryToLender;
    return (T) this;
  }
}

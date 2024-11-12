package com.lighthouse.library.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lighthouse.library.model.model.Library;
import com.lighthouse.library.model.validation.IdValid;
import com.lighthouse.library.model.validation.Update;

/** Object Used to Update Library */
@IdValid.List({
  @IdValid(
      field = "id",
      fieldType = Library.class,
      targetField = "library",
      groups = {Update.class})
})
public class LibraryUpdate extends LibraryCreate {

  private String id;

  @JsonIgnore private Library library;

  public LibraryUpdate() {}

  /**
   * @return id
   */
  public String getId() {
    return this.id;
  }

  /**
   * @param id to set
   * @return LibraryUpdate
   */
  public <T extends LibraryUpdate> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return library
   */
  @JsonIgnore
  public Library getLibrary() {
    return this.library;
  }

  /**
   * @param library to set
   * @return LibraryUpdate
   */
  public <T extends LibraryUpdate> T setLibrary(Library library) {
    this.library = library;
    return (T) this;
  }
}

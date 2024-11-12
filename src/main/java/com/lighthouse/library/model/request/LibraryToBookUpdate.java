package com.lighthouse.library.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lighthouse.library.model.model.LibraryToBook;
import com.lighthouse.library.model.validation.IdValid;
import com.lighthouse.library.model.validation.Update;

/** Object Used to Update LibraryToBook */
@IdValid.List({
  @IdValid(
      field = "id",
      fieldType = LibraryToBook.class,
      targetField = "libraryToBook",
      groups = {Update.class})
})
public class LibraryToBookUpdate extends LibraryToBookCreate {

  private String id;

  @JsonIgnore private LibraryToBook libraryToBook;

  public LibraryToBookUpdate() {}

  /**
   * @return id
   */
  public String getId() {
    return this.id;
  }

  /**
   * @param id to set
   * @return LibraryToBookUpdate
   */
  public <T extends LibraryToBookUpdate> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return libraryToBook
   */
  @JsonIgnore
  public LibraryToBook getLibraryToBook() {
    return this.libraryToBook;
  }

  /**
   * @param libraryToBook to set
   * @return LibraryToBookUpdate
   */
  public <T extends LibraryToBookUpdate> T setLibraryToBook(LibraryToBook libraryToBook) {
    this.libraryToBook = libraryToBook;
    return (T) this;
  }
}

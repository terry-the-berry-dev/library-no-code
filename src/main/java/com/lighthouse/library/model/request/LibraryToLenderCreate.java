package com.lighthouse.library.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lighthouse.library.model.model.Lender;
import com.lighthouse.library.model.model.Library;
import com.lighthouse.library.model.validation.Create;
import com.lighthouse.library.model.validation.IdValid;
import com.lighthouse.library.model.validation.Update;

/** Object Used to Create LibraryToLender */
@IdValid.List({
  @IdValid(
      field = "lenderId",
      fieldType = Lender.class,
      targetField = "lender",
      groups = {Update.class, Create.class}),
  @IdValid(
      field = "libraryId",
      fieldType = Library.class,
      targetField = "library",
      groups = {Update.class, Create.class})
})
public class LibraryToLenderCreate {

  @JsonIgnore private Lender lender;

  private String lenderId;

  @JsonIgnore private Library library;

  private String libraryId;

  public LibraryToLenderCreate() {}

  /**
   * @return lender
   */
  @JsonIgnore
  public Lender getLender() {
    return this.lender;
  }

  /**
   * @param lender to set
   * @return LibraryToLenderCreate
   */
  public <T extends LibraryToLenderCreate> T setLender(Lender lender) {
    this.lender = lender;
    return (T) this;
  }

  /**
   * @return lenderId
   */
  public String getLenderId() {
    return this.lenderId;
  }

  /**
   * @param lenderId to set
   * @return LibraryToLenderCreate
   */
  public <T extends LibraryToLenderCreate> T setLenderId(String lenderId) {
    this.lenderId = lenderId;
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
   * @return LibraryToLenderCreate
   */
  public <T extends LibraryToLenderCreate> T setLibrary(Library library) {
    this.library = library;
    return (T) this;
  }

  /**
   * @return libraryId
   */
  public String getLibraryId() {
    return this.libraryId;
  }

  /**
   * @param libraryId to set
   * @return LibraryToLenderCreate
   */
  public <T extends LibraryToLenderCreate> T setLibraryId(String libraryId) {
    this.libraryId = libraryId;
    return (T) this;
  }
}

package com.lighthouse.library.model.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class LibraryToLender {

  @Id private String id;

  @ManyToOne(targetEntity = Library.class)
  private Library library;

  @ManyToOne(targetEntity = Lender.class)
  private Lender lender;

  public LibraryToLender() {}

  /**
   * @return id
   */
  @Id
  public String getId() {
    return this.id;
  }

  /**
   * @param id to set
   * @return LibraryToLender
   */
  public <T extends LibraryToLender> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return library
   */
  @ManyToOne(targetEntity = Library.class)
  public Library getLibrary() {
    return this.library;
  }

  /**
   * @param library to set
   * @return LibraryToLender
   */
  public <T extends LibraryToLender> T setLibrary(Library library) {
    this.library = library;
    return (T) this;
  }

  /**
   * @return lender
   */
  @ManyToOne(targetEntity = Lender.class)
  public Lender getLender() {
    return this.lender;
  }

  /**
   * @param lender to set
   * @return LibraryToLender
   */
  public <T extends LibraryToLender> T setLender(Lender lender) {
    this.lender = lender;
    return (T) this;
  }
}

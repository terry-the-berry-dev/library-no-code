package com.lighthouse.library.model.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Library {

  @Id private String id;

  @OneToMany(targetEntity = LibraryToBook.class)
  @JsonIgnore
  private List<LibraryToBook> libraryLibraryToBooks;

  @OneToMany(targetEntity = LibraryToLender.class)
  @JsonIgnore
  private List<LibraryToLender> libraryLibraryToLenders;

  private String name;

  private boolean deleted;

  public Library() {}

  /**
   * @return id
   */
  @Id
  public String getId() {
    return this.id;
  }

  /**
   * @param id to set
   * @return Library
   */
  public <T extends Library> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return libraryLibraryToBooks
   */
  @OneToMany(targetEntity = LibraryToBook.class)
  @JsonIgnore
  public List<LibraryToBook> getLibraryLibraryToBooks() {
    return this.libraryLibraryToBooks;
  }

  /**
   * @param libraryLibraryToBooks to set
   * @return Library
   */
  public <T extends Library> T setLibraryLibraryToBooks(List<LibraryToBook> libraryLibraryToBooks) {
    this.libraryLibraryToBooks = libraryLibraryToBooks;
    return (T) this;
  }

  /**
   * @return libraryLibraryToLenders
   */
  @OneToMany(targetEntity = LibraryToLender.class)
  @JsonIgnore
  public List<LibraryToLender> getLibraryLibraryToLenders() {
    return this.libraryLibraryToLenders;
  }

  /**
   * @param libraryLibraryToLenders to set
   * @return Library
   */
  public <T extends Library> T setLibraryLibraryToLenders(
      List<LibraryToLender> libraryLibraryToLenders) {
    this.libraryLibraryToLenders = libraryLibraryToLenders;
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
   * @return Library
   */
  public <T extends Library> T setName(String name) {
    this.name = name;
    return (T) this;
  }

  /**
   * @return deleted
   */
  public boolean isDeleted() {
    return this.deleted;
  }

  /**
   * @param deleted to set
   * @return Library
   */
  public <T extends Library> T setDeleted(boolean deleted) {
    this.deleted = deleted;
    return (T) this;
  }
}

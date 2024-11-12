package com.lighthouse.library.model.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Lender {

  @Id private String id;

  @OneToMany(targetEntity = LenderToBook.class)
  @JsonIgnore
  private List<LenderToBook> lenderLenderToBooks;

  @OneToMany(targetEntity = LibraryToLender.class)
  @JsonIgnore
  private List<LibraryToLender> lenderLibraryToLenders;

  private String name;

  private boolean deleted;

  public Lender() {}

  /**
   * @return id
   */
  @Id
  public String getId() {
    return this.id;
  }

  /**
   * @param id to set
   * @return Lender
   */
  public <T extends Lender> T setId(String id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return lenderLenderToBooks
   */
  @OneToMany(targetEntity = LenderToBook.class)
  @JsonIgnore
  public List<LenderToBook> getLenderLenderToBooks() {
    return this.lenderLenderToBooks;
  }

  /**
   * @param lenderLenderToBooks to set
   * @return Lender
   */
  public <T extends Lender> T setLenderLenderToBooks(List<LenderToBook> lenderLenderToBooks) {
    this.lenderLenderToBooks = lenderLenderToBooks;
    return (T) this;
  }

  /**
   * @return lenderLibraryToLenders
   */
  @OneToMany(targetEntity = LibraryToLender.class)
  @JsonIgnore
  public List<LibraryToLender> getLenderLibraryToLenders() {
    return this.lenderLibraryToLenders;
  }

  /**
   * @param lenderLibraryToLenders to set
   * @return Lender
   */
  public <T extends Lender> T setLenderLibraryToLenders(
      List<LibraryToLender> lenderLibraryToLenders) {
    this.lenderLibraryToLenders = lenderLibraryToLenders;
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
   * @return Lender
   */
  public <T extends Lender> T setName(String name) {
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
   * @return Lender
   */
  public <T extends Lender> T setDeleted(boolean deleted) {
    this.deleted = deleted;
    return (T) this;
  }
}

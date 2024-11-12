package com.lighthouse.library.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lighthouse.library.model.model.Lender;
import com.lighthouse.library.model.model.Library;
import com.lighthouse.library.model.validation.IdValid;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.Set;

/** Object Used to List LibraryToLender */
@IdValid.List({
  @IdValid(
      field = "lenderIds",
      fieldType = Lender.class,
      targetField = "lenders",
      groups = {}),
  @IdValid(
      field = "libraryIds",
      fieldType = Library.class,
      targetField = "libraries",
      groups = {})
})
public class LibraryToLenderFilter {

  @Min(
      value = 0,
      groups = {})
  private Integer currentPage;

  private Set<String> id;

  private Set<String> lenderIds;

  @JsonIgnore private List<Lender> lenders;

  @JsonIgnore private List<Library> libraries;

  private Set<String> libraryIds;

  @Min(
      value = 1,
      groups = {})
  private Integer pageSize;

  public LibraryToLenderFilter() {}

  /**
   * @return currentPage
   */
  public Integer getCurrentPage() {
    return this.currentPage;
  }

  /**
   * @param currentPage to set
   * @return LibraryToLenderFilter
   */
  public <T extends LibraryToLenderFilter> T setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
    return (T) this;
  }

  /**
   * @return id
   */
  public Set<String> getId() {
    return this.id;
  }

  /**
   * @param id to set
   * @return LibraryToLenderFilter
   */
  public <T extends LibraryToLenderFilter> T setId(Set<String> id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return lenderIds
   */
  public Set<String> getLenderIds() {
    return this.lenderIds;
  }

  /**
   * @param lenderIds to set
   * @return LibraryToLenderFilter
   */
  public <T extends LibraryToLenderFilter> T setLenderIds(Set<String> lenderIds) {
    this.lenderIds = lenderIds;
    return (T) this;
  }

  /**
   * @return lenders
   */
  @JsonIgnore
  public List<Lender> getLenders() {
    return this.lenders;
  }

  /**
   * @param lenders to set
   * @return LibraryToLenderFilter
   */
  public <T extends LibraryToLenderFilter> T setLenders(List<Lender> lenders) {
    this.lenders = lenders;
    return (T) this;
  }

  /**
   * @return libraries
   */
  @JsonIgnore
  public List<Library> getLibraries() {
    return this.libraries;
  }

  /**
   * @param libraries to set
   * @return LibraryToLenderFilter
   */
  public <T extends LibraryToLenderFilter> T setLibraries(List<Library> libraries) {
    this.libraries = libraries;
    return (T) this;
  }

  /**
   * @return libraryIds
   */
  public Set<String> getLibraryIds() {
    return this.libraryIds;
  }

  /**
   * @param libraryIds to set
   * @return LibraryToLenderFilter
   */
  public <T extends LibraryToLenderFilter> T setLibraryIds(Set<String> libraryIds) {
    this.libraryIds = libraryIds;
    return (T) this;
  }

  /**
   * @return pageSize
   */
  public Integer getPageSize() {
    return this.pageSize;
  }

  /**
   * @param pageSize to set
   * @return LibraryToLenderFilter
   */
  public <T extends LibraryToLenderFilter> T setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return (T) this;
  }
}

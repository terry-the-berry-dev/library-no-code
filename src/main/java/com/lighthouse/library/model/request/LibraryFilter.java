package com.lighthouse.library.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lighthouse.library.model.model.LibraryToBook;
import com.lighthouse.library.model.model.LibraryToLender;
import com.lighthouse.library.model.validation.IdValid;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.Set;

/** Object Used to List Library */
@IdValid.List({
  @IdValid(
      field = "libraryLibraryToLendersIds",
      fieldType = LibraryToLender.class,
      targetField = "libraryLibraryToLenderses",
      groups = {}),
  @IdValid(
      field = "libraryLibraryToBooksIds",
      fieldType = LibraryToBook.class,
      targetField = "libraryLibraryToBookses",
      groups = {})
})
public class LibraryFilter {

  @Min(
      value = 0,
      groups = {})
  private Integer currentPage;

  private Set<Boolean> deleted;

  private Set<String> id;

  private Set<String> libraryLibraryToBooksIds;

  @JsonIgnore private List<LibraryToBook> libraryLibraryToBookses;

  private Set<String> libraryLibraryToLendersIds;

  @JsonIgnore private List<LibraryToLender> libraryLibraryToLenderses;

  private Set<String> name;

  private String nameLike;

  @Min(
      value = 1,
      groups = {})
  private Integer pageSize;

  public LibraryFilter() {}

  /**
   * @return currentPage
   */
  public Integer getCurrentPage() {
    return this.currentPage;
  }

  /**
   * @param currentPage to set
   * @return LibraryFilter
   */
  public <T extends LibraryFilter> T setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
    return (T) this;
  }

  /**
   * @return deleted
   */
  public Set<Boolean> getDeleted() {
    return this.deleted;
  }

  /**
   * @param deleted to set
   * @return LibraryFilter
   */
  public <T extends LibraryFilter> T setDeleted(Set<Boolean> deleted) {
    this.deleted = deleted;
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
   * @return LibraryFilter
   */
  public <T extends LibraryFilter> T setId(Set<String> id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return libraryLibraryToBooksIds
   */
  public Set<String> getLibraryLibraryToBooksIds() {
    return this.libraryLibraryToBooksIds;
  }

  /**
   * @param libraryLibraryToBooksIds to set
   * @return LibraryFilter
   */
  public <T extends LibraryFilter> T setLibraryLibraryToBooksIds(
      Set<String> libraryLibraryToBooksIds) {
    this.libraryLibraryToBooksIds = libraryLibraryToBooksIds;
    return (T) this;
  }

  /**
   * @return libraryLibraryToBookses
   */
  @JsonIgnore
  public List<LibraryToBook> getLibraryLibraryToBookses() {
    return this.libraryLibraryToBookses;
  }

  /**
   * @param libraryLibraryToBookses to set
   * @return LibraryFilter
   */
  public <T extends LibraryFilter> T setLibraryLibraryToBookses(
      List<LibraryToBook> libraryLibraryToBookses) {
    this.libraryLibraryToBookses = libraryLibraryToBookses;
    return (T) this;
  }

  /**
   * @return libraryLibraryToLendersIds
   */
  public Set<String> getLibraryLibraryToLendersIds() {
    return this.libraryLibraryToLendersIds;
  }

  /**
   * @param libraryLibraryToLendersIds to set
   * @return LibraryFilter
   */
  public <T extends LibraryFilter> T setLibraryLibraryToLendersIds(
      Set<String> libraryLibraryToLendersIds) {
    this.libraryLibraryToLendersIds = libraryLibraryToLendersIds;
    return (T) this;
  }

  /**
   * @return libraryLibraryToLenderses
   */
  @JsonIgnore
  public List<LibraryToLender> getLibraryLibraryToLenderses() {
    return this.libraryLibraryToLenderses;
  }

  /**
   * @param libraryLibraryToLenderses to set
   * @return LibraryFilter
   */
  public <T extends LibraryFilter> T setLibraryLibraryToLenderses(
      List<LibraryToLender> libraryLibraryToLenderses) {
    this.libraryLibraryToLenderses = libraryLibraryToLenderses;
    return (T) this;
  }

  /**
   * @return name
   */
  public Set<String> getName() {
    return this.name;
  }

  /**
   * @param name to set
   * @return LibraryFilter
   */
  public <T extends LibraryFilter> T setName(Set<String> name) {
    this.name = name;
    return (T) this;
  }

  /**
   * @return nameLike
   */
  public String getNameLike() {
    return this.nameLike;
  }

  /**
   * @param nameLike to set
   * @return LibraryFilter
   */
  public <T extends LibraryFilter> T setNameLike(String nameLike) {
    this.nameLike = nameLike;
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
   * @return LibraryFilter
   */
  public <T extends LibraryFilter> T setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return (T) this;
  }
}

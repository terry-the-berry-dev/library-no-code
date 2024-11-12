package com.lighthouse.library.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lighthouse.library.model.model.LenderToBook;
import com.lighthouse.library.model.model.LibraryToLender;
import com.lighthouse.library.model.validation.IdValid;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.Set;

/** Object Used to List Lender */
@IdValid.List({
  @IdValid(
      field = "lenderLenderToBooksIds",
      fieldType = LenderToBook.class,
      targetField = "lenderLenderToBookses",
      groups = {}),
  @IdValid(
      field = "lenderLibraryToLendersIds",
      fieldType = LibraryToLender.class,
      targetField = "lenderLibraryToLenderses",
      groups = {})
})
public class LenderFilter {

  @Min(
      value = 0,
      groups = {})
  private Integer currentPage;

  private Set<Boolean> deleted;

  private Set<String> id;

  private Set<String> lenderLenderToBooksIds;

  @JsonIgnore private List<LenderToBook> lenderLenderToBookses;

  private Set<String> lenderLibraryToLendersIds;

  @JsonIgnore private List<LibraryToLender> lenderLibraryToLenderses;

  private Set<String> name;

  private String nameLike;

  @Min(
      value = 1,
      groups = {})
  private Integer pageSize;

  public LenderFilter() {}

  /**
   * @return currentPage
   */
  public Integer getCurrentPage() {
    return this.currentPage;
  }

  /**
   * @param currentPage to set
   * @return LenderFilter
   */
  public <T extends LenderFilter> T setCurrentPage(Integer currentPage) {
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
   * @return LenderFilter
   */
  public <T extends LenderFilter> T setDeleted(Set<Boolean> deleted) {
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
   * @return LenderFilter
   */
  public <T extends LenderFilter> T setId(Set<String> id) {
    this.id = id;
    return (T) this;
  }

  /**
   * @return lenderLenderToBooksIds
   */
  public Set<String> getLenderLenderToBooksIds() {
    return this.lenderLenderToBooksIds;
  }

  /**
   * @param lenderLenderToBooksIds to set
   * @return LenderFilter
   */
  public <T extends LenderFilter> T setLenderLenderToBooksIds(Set<String> lenderLenderToBooksIds) {
    this.lenderLenderToBooksIds = lenderLenderToBooksIds;
    return (T) this;
  }

  /**
   * @return lenderLenderToBookses
   */
  @JsonIgnore
  public List<LenderToBook> getLenderLenderToBookses() {
    return this.lenderLenderToBookses;
  }

  /**
   * @param lenderLenderToBookses to set
   * @return LenderFilter
   */
  public <T extends LenderFilter> T setLenderLenderToBookses(
      List<LenderToBook> lenderLenderToBookses) {
    this.lenderLenderToBookses = lenderLenderToBookses;
    return (T) this;
  }

  /**
   * @return lenderLibraryToLendersIds
   */
  public Set<String> getLenderLibraryToLendersIds() {
    return this.lenderLibraryToLendersIds;
  }

  /**
   * @param lenderLibraryToLendersIds to set
   * @return LenderFilter
   */
  public <T extends LenderFilter> T setLenderLibraryToLendersIds(
      Set<String> lenderLibraryToLendersIds) {
    this.lenderLibraryToLendersIds = lenderLibraryToLendersIds;
    return (T) this;
  }

  /**
   * @return lenderLibraryToLenderses
   */
  @JsonIgnore
  public List<LibraryToLender> getLenderLibraryToLenderses() {
    return this.lenderLibraryToLenderses;
  }

  /**
   * @param lenderLibraryToLenderses to set
   * @return LenderFilter
   */
  public <T extends LenderFilter> T setLenderLibraryToLenderses(
      List<LibraryToLender> lenderLibraryToLenderses) {
    this.lenderLibraryToLenderses = lenderLibraryToLenderses;
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
   * @return LenderFilter
   */
  public <T extends LenderFilter> T setName(Set<String> name) {
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
   * @return LenderFilter
   */
  public <T extends LenderFilter> T setNameLike(String nameLike) {
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
   * @return LenderFilter
   */
  public <T extends LenderFilter> T setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return (T) this;
  }
}

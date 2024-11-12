package com.lighthouse.library.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lighthouse.library.model.model.AuthorToBook;
import com.lighthouse.library.model.validation.IdValid;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.Set;

/** Object Used to List Author */
@IdValid.List({
  @IdValid(
      field = "authorAuthorToBooksIds",
      fieldType = AuthorToBook.class,
      targetField = "authorAuthorToBookses",
      groups = {})
})
public class AuthorFilter {

  private Set<String> authorAuthorToBooksIds;

  @JsonIgnore private List<AuthorToBook> authorAuthorToBookses;

  @Min(
      value = 0,
      groups = {})
  private Integer currentPage;

  private Set<String> deleted;

  private String deletedLike;

  private Set<String> id;

  private Set<String> name;

  private String nameLike;

  @Min(
      value = 1,
      groups = {})
  private Integer pageSize;

  public AuthorFilter() {}

  /**
   * @return authorAuthorToBooksIds
   */
  public Set<String> getAuthorAuthorToBooksIds() {
    return this.authorAuthorToBooksIds;
  }

  /**
   * @param authorAuthorToBooksIds to set
   * @return AuthorFilter
   */
  public <T extends AuthorFilter> T setAuthorAuthorToBooksIds(Set<String> authorAuthorToBooksIds) {
    this.authorAuthorToBooksIds = authorAuthorToBooksIds;
    return (T) this;
  }

  /**
   * @return authorAuthorToBookses
   */
  @JsonIgnore
  public List<AuthorToBook> getAuthorAuthorToBookses() {
    return this.authorAuthorToBookses;
  }

  /**
   * @param authorAuthorToBookses to set
   * @return AuthorFilter
   */
  public <T extends AuthorFilter> T setAuthorAuthorToBookses(
      List<AuthorToBook> authorAuthorToBookses) {
    this.authorAuthorToBookses = authorAuthorToBookses;
    return (T) this;
  }

  /**
   * @return currentPage
   */
  public Integer getCurrentPage() {
    return this.currentPage;
  }

  /**
   * @param currentPage to set
   * @return AuthorFilter
   */
  public <T extends AuthorFilter> T setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
    return (T) this;
  }

  /**
   * @return deleted
   */
  public Set<String> getDeleted() {
    return this.deleted;
  }

  /**
   * @param deleted to set
   * @return AuthorFilter
   */
  public <T extends AuthorFilter> T setDeleted(Set<String> deleted) {
    this.deleted = deleted;
    return (T) this;
  }

  /**
   * @return deletedLike
   */
  public String getDeletedLike() {
    return this.deletedLike;
  }

  /**
   * @param deletedLike to set
   * @return AuthorFilter
   */
  public <T extends AuthorFilter> T setDeletedLike(String deletedLike) {
    this.deletedLike = deletedLike;
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
   * @return AuthorFilter
   */
  public <T extends AuthorFilter> T setId(Set<String> id) {
    this.id = id;
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
   * @return AuthorFilter
   */
  public <T extends AuthorFilter> T setName(Set<String> name) {
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
   * @return AuthorFilter
   */
  public <T extends AuthorFilter> T setNameLike(String nameLike) {
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
   * @return AuthorFilter
   */
  public <T extends AuthorFilter> T setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return (T) this;
  }
}

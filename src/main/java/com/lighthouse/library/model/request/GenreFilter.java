package com.lighthouse.library.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lighthouse.library.model.model.BookToGenre;
import com.lighthouse.library.model.validation.IdValid;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.Set;

/** Object Used to List Genre */
@IdValid.List({
  @IdValid(
      field = "genreBookToGenresIds",
      fieldType = BookToGenre.class,
      targetField = "genreBookToGenreses",
      groups = {})
})
public class GenreFilter {

  @Min(
      value = 0,
      groups = {})
  private Integer currentPage;

  private Set<Boolean> deleted;

  private Set<String> genreBookToGenresIds;

  @JsonIgnore private List<BookToGenre> genreBookToGenreses;

  private Set<String> id;

  private Set<String> name;

  private String nameLike;

  @Min(
      value = 1,
      groups = {})
  private Integer pageSize;

  public GenreFilter() {}

  /**
   * @return currentPage
   */
  public Integer getCurrentPage() {
    return this.currentPage;
  }

  /**
   * @param currentPage to set
   * @return GenreFilter
   */
  public <T extends GenreFilter> T setCurrentPage(Integer currentPage) {
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
   * @return GenreFilter
   */
  public <T extends GenreFilter> T setDeleted(Set<Boolean> deleted) {
    this.deleted = deleted;
    return (T) this;
  }

  /**
   * @return genreBookToGenresIds
   */
  public Set<String> getGenreBookToGenresIds() {
    return this.genreBookToGenresIds;
  }

  /**
   * @param genreBookToGenresIds to set
   * @return GenreFilter
   */
  public <T extends GenreFilter> T setGenreBookToGenresIds(Set<String> genreBookToGenresIds) {
    this.genreBookToGenresIds = genreBookToGenresIds;
    return (T) this;
  }

  /**
   * @return genreBookToGenreses
   */
  @JsonIgnore
  public List<BookToGenre> getGenreBookToGenreses() {
    return this.genreBookToGenreses;
  }

  /**
   * @param genreBookToGenreses to set
   * @return GenreFilter
   */
  public <T extends GenreFilter> T setGenreBookToGenreses(List<BookToGenre> genreBookToGenreses) {
    this.genreBookToGenreses = genreBookToGenreses;
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
   * @return GenreFilter
   */
  public <T extends GenreFilter> T setId(Set<String> id) {
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
   * @return GenreFilter
   */
  public <T extends GenreFilter> T setName(Set<String> name) {
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
   * @return GenreFilter
   */
  public <T extends GenreFilter> T setNameLike(String nameLike) {
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
   * @return GenreFilter
   */
  public <T extends GenreFilter> T setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return (T) this;
  }
}

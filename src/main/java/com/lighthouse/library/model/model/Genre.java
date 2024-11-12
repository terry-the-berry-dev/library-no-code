package com.lighthouse.library.model.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Genre {

  @OneToMany(targetEntity = BookToGenre.class)
  @JsonIgnore
  private List<BookToGenre> genreBookToGenres;

  @Id private String id;

  private String name;

  private boolean deleted;

  public Genre() {}

  /**
   * @return genreBookToGenres
   */
  @OneToMany(targetEntity = BookToGenre.class)
  @JsonIgnore
  public List<BookToGenre> getGenreBookToGenres() {
    return this.genreBookToGenres;
  }

  /**
   * @param genreBookToGenres to set
   * @return Genre
   */
  public <T extends Genre> T setGenreBookToGenres(List<BookToGenre> genreBookToGenres) {
    this.genreBookToGenres = genreBookToGenres;
    return (T) this;
  }

  /**
   * @return id
   */
  @Id
  public String getId() {
    return this.id;
  }

  /**
   * @param id to set
   * @return Genre
   */
  public <T extends Genre> T setId(String id) {
    this.id = id;
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
   * @return Genre
   */
  public <T extends Genre> T setName(String name) {
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
   * @return Genre
   */
  public <T extends Genre> T setDeleted(boolean deleted) {
    this.deleted = deleted;
    return (T) this;
  }
}

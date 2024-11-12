package com.lighthouse.library.model.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Author {

  @OneToMany(targetEntity = AuthorToBook.class)
  @JsonIgnore
  private List<AuthorToBook> authorAuthorToBooks;

  @Id private String id;

  private String name;

  private String deleted;

  public Author() {}

  /**
   * @return authorAuthorToBooks
   */
  @OneToMany(targetEntity = AuthorToBook.class)
  @JsonIgnore
  public List<AuthorToBook> getAuthorAuthorToBooks() {
    return this.authorAuthorToBooks;
  }

  /**
   * @param authorAuthorToBooks to set
   * @return Author
   */
  public <T extends Author> T setAuthorAuthorToBooks(List<AuthorToBook> authorAuthorToBooks) {
    this.authorAuthorToBooks = authorAuthorToBooks;
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
   * @return Author
   */
  public <T extends Author> T setId(String id) {
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
   * @return Author
   */
  public <T extends Author> T setName(String name) {
    this.name = name;
    return (T) this;
  }

  /**
   * @return deleted
   */
  public String getDeleted() {
    return this.deleted;
  }

  /**
   * @param deleted to set
   * @return Author
   */
  public <T extends Author> T setDeleted(String deleted) {
    this.deleted = deleted;
    return (T) this;
  }
}

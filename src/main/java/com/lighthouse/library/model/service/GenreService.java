package com.lighthouse.library.model.service;

import com.lighthouse.library.model.data.GenreRepository;
import com.lighthouse.library.model.model.Genre;
import com.lighthouse.library.model.model.Genre_;
import com.lighthouse.library.model.request.GenreCreate;
import com.lighthouse.library.model.request.GenreFilter;
import com.lighthouse.library.model.request.GenreUpdate;
import com.lighthouse.library.model.response.PaginationResponse;
import com.lighthouse.library.model.security.UserSecurityContext;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class GenreService {

  @Autowired private GenreRepository repository;

  /**
   * @param genreCreate Object Used to Create Genre
   * @param securityContext
   * @return created Genre
   */
  public Genre createGenre(GenreCreate genreCreate, UserSecurityContext securityContext) {

    Genre genre = createGenreNoMerge(genreCreate, securityContext);
    genre = this.repository.merge(genre);
    return genre;
  }

  /**
   * @param genreCreate Object Used to Create Genre
   * @param securityContext
   * @return created Genre unmerged
   */
  public Genre createGenreNoMerge(GenreCreate genreCreate, UserSecurityContext securityContext) {

    Genre genre = new Genre();
    genre.setId(UUID.randomUUID().toString());

    updateGenreNoMerge(genre, genreCreate);

    return genre;
  }

  /**
   * @param genreCreate Object Used to Create Genre
   * @param genre
   * @return if genre was updated
   */
  public boolean updateGenreNoMerge(Genre genre, GenreCreate genreCreate) {

    boolean update = false;

    if (genreCreate.getName() != null && (!genreCreate.getName().equals(genre.getName()))) {
      genre.setName(genreCreate.getName());
      update = true;
    }

    if (genreCreate.getDeleted() != null && (!genreCreate.getDeleted().equals(genre.isDeleted()))) {
      genre.setDeleted(genreCreate.getDeleted());
      update = true;
    }

    return update;
  }

  /**
   * @param genreUpdate
   * @param securityContext
   * @return genre
   */
  public Genre updateGenre(GenreUpdate genreUpdate, UserSecurityContext securityContext) {

    Genre genre = genreUpdate.getGenre();

    if (updateGenreNoMerge(genre, genreUpdate)) {
      genre = this.repository.merge(genre);
    }

    return genre;
  }

  /**
   * @param genreFilter Object Used to List Genre
   * @param securityContext
   * @return PaginationResponse<Genre> containing paging information for Genre
   */
  public PaginationResponse<Genre> getAllGenres(
      GenreFilter genreFilter, UserSecurityContext securityContext) {

    List<Genre> list = listAllGenres(genreFilter, securityContext);
    long count = this.repository.countAllGenres(genreFilter, securityContext);

    return new PaginationResponse<>(list, genreFilter.getPageSize(), count);
  }

  /**
   * @param genreFilter Object Used to List Genre
   * @param securityContext
   * @return List of Genre
   */
  public List<Genre> listAllGenres(GenreFilter genreFilter, UserSecurityContext securityContext) {

    return this.repository.listAllGenres(genreFilter, securityContext);
  }

  public Genre deleteGenre(String id, UserSecurityContext securityContext) {

    Genre genre = this.repository.getByIdOrNull(Genre.class, Genre_.id, id, securityContext);

    if (genre == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Genre not found");
    }

    this.repository.remove(genre);

    return genre;
  }

  public <T extends Genre, I> List<T> listByIds(
      Class<T> c,
      SingularAttribute<? super T, I> idField,
      Set<I> ids,
      UserSecurityContext securityContext) {
    return repository.listByIds(c, idField, ids, securityContext);
  }

  public <T extends Genre, I> T getByIdOrNull(
      Class<T> c,
      SingularAttribute<? super T, I> idField,
      I id,
      UserSecurityContext securityContext) {
    return repository.getByIdOrNull(c, idField, id, securityContext);
  }

  public <T extends Genre, I> T getByIdOrNull(
      Class<T> c, SingularAttribute<? super T, I> idField, I id) {
    return repository.getByIdOrNull(c, idField, id);
  }

  public <T extends Genre, I> List<T> listByIds(
      Class<T> c, SingularAttribute<? super T, I> idField, Set<I> ids) {
    return repository.listByIds(c, idField, ids);
  }

  public <T> T merge(T base) {
    return this.repository.merge(base);
  }

  public void massMerge(List<?> toMerge) {
    this.repository.massMerge(toMerge);
  }
}

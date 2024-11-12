package com.lighthouse.library.model.service;

import com.lighthouse.library.model.data.LibraryToLenderRepository;
import com.lighthouse.library.model.model.LibraryToLender;
import com.lighthouse.library.model.model.LibraryToLender_;
import com.lighthouse.library.model.request.LibraryToLenderCreate;
import com.lighthouse.library.model.request.LibraryToLenderFilter;
import com.lighthouse.library.model.request.LibraryToLenderUpdate;
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
public class LibraryToLenderService {

  @Autowired private LibraryToLenderRepository repository;

  /**
   * @param libraryToLenderCreate Object Used to Create LibraryToLender
   * @param securityContext
   * @return created LibraryToLender
   */
  public LibraryToLender createLibraryToLender(
      LibraryToLenderCreate libraryToLenderCreate, UserSecurityContext securityContext) {

    LibraryToLender libraryToLender =
        createLibraryToLenderNoMerge(libraryToLenderCreate, securityContext);
    libraryToLender = this.repository.merge(libraryToLender);
    return libraryToLender;
  }

  /**
   * @param libraryToLenderCreate Object Used to Create LibraryToLender
   * @param securityContext
   * @return created LibraryToLender unmerged
   */
  public LibraryToLender createLibraryToLenderNoMerge(
      LibraryToLenderCreate libraryToLenderCreate, UserSecurityContext securityContext) {

    LibraryToLender libraryToLender = new LibraryToLender();
    libraryToLender.setId(UUID.randomUUID().toString());

    updateLibraryToLenderNoMerge(libraryToLender, libraryToLenderCreate);

    return libraryToLender;
  }

  /**
   * @param libraryToLenderCreate Object Used to Create LibraryToLender
   * @param libraryToLender
   * @return if libraryToLender was updated
   */
  public boolean updateLibraryToLenderNoMerge(
      LibraryToLender libraryToLender, LibraryToLenderCreate libraryToLenderCreate) {

    boolean update = false;

    if (libraryToLenderCreate.getLibrary() != null
        && (libraryToLender.getLibrary() == null
            || !libraryToLenderCreate
                .getLibrary()
                .getId()
                .equals(libraryToLender.getLibrary().getId()))) {
      libraryToLender.setLibrary(libraryToLenderCreate.getLibrary());
      update = true;
    }

    if (libraryToLenderCreate.getLender() != null
        && (libraryToLender.getLender() == null
            || !libraryToLenderCreate
                .getLender()
                .getId()
                .equals(libraryToLender.getLender().getId()))) {
      libraryToLender.setLender(libraryToLenderCreate.getLender());
      update = true;
    }

    return update;
  }

  /**
   * @param libraryToLenderUpdate
   * @param securityContext
   * @return libraryToLender
   */
  public LibraryToLender updateLibraryToLender(
      LibraryToLenderUpdate libraryToLenderUpdate, UserSecurityContext securityContext) {

    LibraryToLender libraryToLender = libraryToLenderUpdate.getLibraryToLender();

    if (updateLibraryToLenderNoMerge(libraryToLender, libraryToLenderUpdate)) {
      libraryToLender = this.repository.merge(libraryToLender);
    }

    return libraryToLender;
  }

  /**
   * @param libraryToLenderFilter Object Used to List LibraryToLender
   * @param securityContext
   * @return PaginationResponse<LibraryToLender> containing paging information for LibraryToLender
   */
  public PaginationResponse<LibraryToLender> getAllLibraryToLenders(
      LibraryToLenderFilter libraryToLenderFilter, UserSecurityContext securityContext) {

    List<LibraryToLender> list = listAllLibraryToLenders(libraryToLenderFilter, securityContext);
    long count = this.repository.countAllLibraryToLenders(libraryToLenderFilter, securityContext);

    return new PaginationResponse<>(list, libraryToLenderFilter.getPageSize(), count);
  }

  /**
   * @param libraryToLenderFilter Object Used to List LibraryToLender
   * @param securityContext
   * @return List of LibraryToLender
   */
  public List<LibraryToLender> listAllLibraryToLenders(
      LibraryToLenderFilter libraryToLenderFilter, UserSecurityContext securityContext) {

    return this.repository.listAllLibraryToLenders(libraryToLenderFilter, securityContext);
  }

  public LibraryToLender deleteLibraryToLender(String id, UserSecurityContext securityContext) {

    LibraryToLender libraryToLender =
        this.repository.getByIdOrNull(
            LibraryToLender.class, LibraryToLender_.id, id, securityContext);

    if (libraryToLender == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LibraryToLender not found");
    }

    this.repository.remove(libraryToLender);

    return libraryToLender;
  }

  public <T extends LibraryToLender, I> List<T> listByIds(
      Class<T> c,
      SingularAttribute<? super T, I> idField,
      Set<I> ids,
      UserSecurityContext securityContext) {
    return repository.listByIds(c, idField, ids, securityContext);
  }

  public <T extends LibraryToLender, I> T getByIdOrNull(
      Class<T> c,
      SingularAttribute<? super T, I> idField,
      I id,
      UserSecurityContext securityContext) {
    return repository.getByIdOrNull(c, idField, id, securityContext);
  }

  public <T extends LibraryToLender, I> T getByIdOrNull(
      Class<T> c, SingularAttribute<? super T, I> idField, I id) {
    return repository.getByIdOrNull(c, idField, id);
  }

  public <T extends LibraryToLender, I> List<T> listByIds(
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

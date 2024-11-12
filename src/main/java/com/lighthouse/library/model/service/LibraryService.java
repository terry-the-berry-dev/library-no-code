package com.lighthouse.library.model.service;

import com.lighthouse.library.model.data.LibraryRepository;
import com.lighthouse.library.model.model.Library;
import com.lighthouse.library.model.model.Library_;
import com.lighthouse.library.model.request.LibraryCreate;
import com.lighthouse.library.model.request.LibraryFilter;
import com.lighthouse.library.model.request.LibraryUpdate;
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
public class LibraryService {

  @Autowired private LibraryRepository repository;

  /**
   * @param libraryCreate Object Used to Create Library
   * @param securityContext
   * @return created Library
   */
  public Library createLibrary(LibraryCreate libraryCreate, UserSecurityContext securityContext) {

    Library library = createLibraryNoMerge(libraryCreate, securityContext);
    library = this.repository.merge(library);
    return library;
  }

  /**
   * @param libraryCreate Object Used to Create Library
   * @param securityContext
   * @return created Library unmerged
   */
  public Library createLibraryNoMerge(
      LibraryCreate libraryCreate, UserSecurityContext securityContext) {

    Library library = new Library();
    library.setId(UUID.randomUUID().toString());

    updateLibraryNoMerge(library, libraryCreate);

    return library;
  }

  /**
   * @param libraryCreate Object Used to Create Library
   * @param library
   * @return if library was updated
   */
  public boolean updateLibraryNoMerge(Library library, LibraryCreate libraryCreate) {

    boolean update = false;

    if (libraryCreate.getName() != null && (!libraryCreate.getName().equals(library.getName()))) {
      library.setName(libraryCreate.getName());
      update = true;
    }

    if (libraryCreate.getDeleted() != null
        && (!libraryCreate.getDeleted().equals(library.isDeleted()))) {
      library.setDeleted(libraryCreate.getDeleted());
      update = true;
    }

    return update;
  }

  /**
   * @param libraryUpdate
   * @param securityContext
   * @return library
   */
  public Library updateLibrary(LibraryUpdate libraryUpdate, UserSecurityContext securityContext) {

    Library library = libraryUpdate.getLibrary();

    if (updateLibraryNoMerge(library, libraryUpdate)) {
      library = this.repository.merge(library);
    }

    return library;
  }

  /**
   * @param libraryFilter Object Used to List Library
   * @param securityContext
   * @return PaginationResponse<Library> containing paging information for Library
   */
  public PaginationResponse<Library> getAllLibraries(
      LibraryFilter libraryFilter, UserSecurityContext securityContext) {

    List<Library> list = listAllLibraries(libraryFilter, securityContext);
    long count = this.repository.countAllLibraries(libraryFilter, securityContext);

    return new PaginationResponse<>(list, libraryFilter.getPageSize(), count);
  }

  /**
   * @param libraryFilter Object Used to List Library
   * @param securityContext
   * @return List of Library
   */
  public List<Library> listAllLibraries(
      LibraryFilter libraryFilter, UserSecurityContext securityContext) {

    return this.repository.listAllLibraries(libraryFilter, securityContext);
  }

  public Library deleteLibrary(String id, UserSecurityContext securityContext) {

    Library library =
        this.repository.getByIdOrNull(Library.class, Library_.id, id, securityContext);

    if (library == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Library not found");
    }

    this.repository.remove(library);

    return library;
  }

  public <T extends Library, I> List<T> listByIds(
      Class<T> c,
      SingularAttribute<? super T, I> idField,
      Set<I> ids,
      UserSecurityContext securityContext) {
    return repository.listByIds(c, idField, ids, securityContext);
  }

  public <T extends Library, I> T getByIdOrNull(
      Class<T> c,
      SingularAttribute<? super T, I> idField,
      I id,
      UserSecurityContext securityContext) {
    return repository.getByIdOrNull(c, idField, id, securityContext);
  }

  public <T extends Library, I> T getByIdOrNull(
      Class<T> c, SingularAttribute<? super T, I> idField, I id) {
    return repository.getByIdOrNull(c, idField, id);
  }

  public <T extends Library, I> List<T> listByIds(
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

package com.lighthouse.library.model.controller;

import com.lighthouse.library.model.model.Library;
import com.lighthouse.library.model.request.LibraryCreate;
import com.lighthouse.library.model.request.LibraryFilter;
import com.lighthouse.library.model.request.LibraryUpdate;
import com.lighthouse.library.model.response.PaginationResponse;
import com.lighthouse.library.model.security.UserSecurityContext;
import com.lighthouse.library.model.service.LibraryService;
import com.lighthouse.library.model.validation.Create;
import com.lighthouse.library.model.validation.Update;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("Library")
@Tag(name = "Library")
public class LibraryController {

  @Autowired private LibraryService libraryService;

  @DeleteMapping("{id}")
  @Operation(summary = "deleteLibrary", description = "Deletes Library")
  public Library deleteLibrary(@PathVariable("id") String id, Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return libraryService.deleteLibrary(id, securityContext);
  }

  @PostMapping("getAllLibraries")
  @Operation(summary = "getAllLibraries", description = "lists Libraries")
  public PaginationResponse<Library> getAllLibraries(
      @Valid @RequestBody LibraryFilter libraryFilter, Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return libraryService.getAllLibraries(libraryFilter, securityContext);
  }

  @PutMapping("updateLibrary")
  @Operation(summary = "updateLibrary", description = "Updates Library")
  public Library updateLibrary(
      @Validated(Update.class) @RequestBody LibraryUpdate libraryUpdate,
      Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return libraryService.updateLibrary(libraryUpdate, securityContext);
  }

  @PostMapping("createLibrary")
  @Operation(summary = "createLibrary", description = "Creates Library")
  public Library createLibrary(
      @Validated(Create.class) @RequestBody LibraryCreate libraryCreate,
      Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return libraryService.createLibrary(libraryCreate, securityContext);
  }
}

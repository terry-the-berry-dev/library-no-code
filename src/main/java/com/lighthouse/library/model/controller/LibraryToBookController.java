package com.lighthouse.library.model.controller;

import com.lighthouse.library.model.model.LibraryToBook;
import com.lighthouse.library.model.request.LibraryToBookCreate;
import com.lighthouse.library.model.request.LibraryToBookFilter;
import com.lighthouse.library.model.request.LibraryToBookUpdate;
import com.lighthouse.library.model.response.PaginationResponse;
import com.lighthouse.library.model.security.UserSecurityContext;
import com.lighthouse.library.model.service.LibraryToBookService;
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
@RequestMapping("LibraryToBook")
@Tag(name = "LibraryToBook")
public class LibraryToBookController {

  @Autowired private LibraryToBookService libraryToBookService;

  @DeleteMapping("{id}")
  @Operation(summary = "deleteLibraryToBook", description = "Deletes LibraryToBook")
  public LibraryToBook deleteLibraryToBook(
      @PathVariable("id") String id, Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return libraryToBookService.deleteLibraryToBook(id, securityContext);
  }

  @PostMapping("getAllLibraryToBooks")
  @Operation(summary = "getAllLibraryToBooks", description = "lists LibraryToBooks")
  public PaginationResponse<LibraryToBook> getAllLibraryToBooks(
      @Valid @RequestBody LibraryToBookFilter libraryToBookFilter, Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return libraryToBookService.getAllLibraryToBooks(libraryToBookFilter, securityContext);
  }

  @PutMapping("updateLibraryToBook")
  @Operation(summary = "updateLibraryToBook", description = "Updates LibraryToBook")
  public LibraryToBook updateLibraryToBook(
      @Validated(Update.class) @RequestBody LibraryToBookUpdate libraryToBookUpdate,
      Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return libraryToBookService.updateLibraryToBook(libraryToBookUpdate, securityContext);
  }

  @PostMapping("createLibraryToBook")
  @Operation(summary = "createLibraryToBook", description = "Creates LibraryToBook")
  public LibraryToBook createLibraryToBook(
      @Validated(Create.class) @RequestBody LibraryToBookCreate libraryToBookCreate,
      Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return libraryToBookService.createLibraryToBook(libraryToBookCreate, securityContext);
  }
}

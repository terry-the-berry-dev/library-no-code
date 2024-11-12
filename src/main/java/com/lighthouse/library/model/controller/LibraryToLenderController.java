package com.lighthouse.library.model.controller;

import com.lighthouse.library.model.model.LibraryToLender;
import com.lighthouse.library.model.request.LibraryToLenderCreate;
import com.lighthouse.library.model.request.LibraryToLenderFilter;
import com.lighthouse.library.model.request.LibraryToLenderUpdate;
import com.lighthouse.library.model.response.PaginationResponse;
import com.lighthouse.library.model.security.UserSecurityContext;
import com.lighthouse.library.model.service.LibraryToLenderService;
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
@RequestMapping("LibraryToLender")
@Tag(name = "LibraryToLender")
public class LibraryToLenderController {

  @Autowired private LibraryToLenderService libraryToLenderService;

  @DeleteMapping("{id}")
  @Operation(summary = "deleteLibraryToLender", description = "Deletes LibraryToLender")
  public LibraryToLender deleteLibraryToLender(
      @PathVariable("id") String id, Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return libraryToLenderService.deleteLibraryToLender(id, securityContext);
  }

  @PostMapping("getAllLibraryToLenders")
  @Operation(summary = "getAllLibraryToLenders", description = "lists LibraryToLenders")
  public PaginationResponse<LibraryToLender> getAllLibraryToLenders(
      @Valid @RequestBody LibraryToLenderFilter libraryToLenderFilter,
      Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return libraryToLenderService.getAllLibraryToLenders(libraryToLenderFilter, securityContext);
  }

  @PutMapping("updateLibraryToLender")
  @Operation(summary = "updateLibraryToLender", description = "Updates LibraryToLender")
  public LibraryToLender updateLibraryToLender(
      @Validated(Update.class) @RequestBody LibraryToLenderUpdate libraryToLenderUpdate,
      Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return libraryToLenderService.updateLibraryToLender(libraryToLenderUpdate, securityContext);
  }

  @PostMapping("createLibraryToLender")
  @Operation(summary = "createLibraryToLender", description = "Creates LibraryToLender")
  public LibraryToLender createLibraryToLender(
      @Validated(Create.class) @RequestBody LibraryToLenderCreate libraryToLenderCreate,
      Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return libraryToLenderService.createLibraryToLender(libraryToLenderCreate, securityContext);
  }
}

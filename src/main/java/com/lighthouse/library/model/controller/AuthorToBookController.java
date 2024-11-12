package com.lighthouse.library.model.controller;

import com.lighthouse.library.model.model.AuthorToBook;
import com.lighthouse.library.model.request.AuthorToBookCreate;
import com.lighthouse.library.model.request.AuthorToBookFilter;
import com.lighthouse.library.model.request.AuthorToBookUpdate;
import com.lighthouse.library.model.response.PaginationResponse;
import com.lighthouse.library.model.security.UserSecurityContext;
import com.lighthouse.library.model.service.AuthorToBookService;
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
@RequestMapping("AuthorToBook")
@Tag(name = "AuthorToBook")
public class AuthorToBookController {

  @Autowired private AuthorToBookService authorToBookService;

  @DeleteMapping("{id}")
  @Operation(summary = "deleteAuthorToBook", description = "Deletes AuthorToBook")
  public AuthorToBook deleteAuthorToBook(
      @PathVariable("id") String id, Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return authorToBookService.deleteAuthorToBook(id, securityContext);
  }

  @PostMapping("getAllAuthorToBooks")
  @Operation(summary = "getAllAuthorToBooks", description = "lists AuthorToBooks")
  public PaginationResponse<AuthorToBook> getAllAuthorToBooks(
      @Valid @RequestBody AuthorToBookFilter authorToBookFilter, Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return authorToBookService.getAllAuthorToBooks(authorToBookFilter, securityContext);
  }

  @PutMapping("updateAuthorToBook")
  @Operation(summary = "updateAuthorToBook", description = "Updates AuthorToBook")
  public AuthorToBook updateAuthorToBook(
      @Validated(Update.class) @RequestBody AuthorToBookUpdate authorToBookUpdate,
      Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return authorToBookService.updateAuthorToBook(authorToBookUpdate, securityContext);
  }

  @PostMapping("createAuthorToBook")
  @Operation(summary = "createAuthorToBook", description = "Creates AuthorToBook")
  public AuthorToBook createAuthorToBook(
      @Validated(Create.class) @RequestBody AuthorToBookCreate authorToBookCreate,
      Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return authorToBookService.createAuthorToBook(authorToBookCreate, securityContext);
  }
}

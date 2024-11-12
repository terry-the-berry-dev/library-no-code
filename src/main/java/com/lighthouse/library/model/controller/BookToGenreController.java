package com.lighthouse.library.model.controller;

import com.lighthouse.library.model.model.BookToGenre;
import com.lighthouse.library.model.request.BookToGenreCreate;
import com.lighthouse.library.model.request.BookToGenreFilter;
import com.lighthouse.library.model.request.BookToGenreUpdate;
import com.lighthouse.library.model.response.PaginationResponse;
import com.lighthouse.library.model.security.UserSecurityContext;
import com.lighthouse.library.model.service.BookToGenreService;
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
@RequestMapping("BookToGenre")
@Tag(name = "BookToGenre")
public class BookToGenreController {

  @Autowired private BookToGenreService bookToGenreService;

  @DeleteMapping("{id}")
  @Operation(summary = "deleteBookToGenre", description = "Deletes BookToGenre")
  public BookToGenre deleteBookToGenre(
      @PathVariable("id") String id, Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return bookToGenreService.deleteBookToGenre(id, securityContext);
  }

  @PostMapping("getAllBookToGenres")
  @Operation(summary = "getAllBookToGenres", description = "lists BookToGenres")
  public PaginationResponse<BookToGenre> getAllBookToGenres(
      @Valid @RequestBody BookToGenreFilter bookToGenreFilter, Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return bookToGenreService.getAllBookToGenres(bookToGenreFilter, securityContext);
  }

  @PutMapping("updateBookToGenre")
  @Operation(summary = "updateBookToGenre", description = "Updates BookToGenre")
  public BookToGenre updateBookToGenre(
      @Validated(Update.class) @RequestBody BookToGenreUpdate bookToGenreUpdate,
      Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return bookToGenreService.updateBookToGenre(bookToGenreUpdate, securityContext);
  }

  @PostMapping("createBookToGenre")
  @Operation(summary = "createBookToGenre", description = "Creates BookToGenre")
  public BookToGenre createBookToGenre(
      @Validated(Create.class) @RequestBody BookToGenreCreate bookToGenreCreate,
      Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return bookToGenreService.createBookToGenre(bookToGenreCreate, securityContext);
  }
}

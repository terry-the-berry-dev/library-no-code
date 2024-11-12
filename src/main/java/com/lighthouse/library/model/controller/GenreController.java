package com.lighthouse.library.model.controller;

import com.lighthouse.library.model.model.Genre;
import com.lighthouse.library.model.request.GenreCreate;
import com.lighthouse.library.model.request.GenreFilter;
import com.lighthouse.library.model.request.GenreUpdate;
import com.lighthouse.library.model.response.PaginationResponse;
import com.lighthouse.library.model.security.UserSecurityContext;
import com.lighthouse.library.model.service.GenreService;
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
@RequestMapping("Genre")
@Tag(name = "Genre")
public class GenreController {

  @Autowired private GenreService genreService;

  @DeleteMapping("{id}")
  @Operation(summary = "deleteGenre", description = "Deletes Genre")
  public Genre deleteGenre(@PathVariable("id") String id, Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return genreService.deleteGenre(id, securityContext);
  }

  @PostMapping("getAllGenres")
  @Operation(summary = "getAllGenres", description = "lists Genres")
  public PaginationResponse<Genre> getAllGenres(
      @Valid @RequestBody GenreFilter genreFilter, Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return genreService.getAllGenres(genreFilter, securityContext);
  }

  @PutMapping("updateGenre")
  @Operation(summary = "updateGenre", description = "Updates Genre")
  public Genre updateGenre(
      @Validated(Update.class) @RequestBody GenreUpdate genreUpdate,
      Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return genreService.updateGenre(genreUpdate, securityContext);
  }

  @PostMapping("createGenre")
  @Operation(summary = "createGenre", description = "Creates Genre")
  public Genre createGenre(
      @Validated(Create.class) @RequestBody GenreCreate genreCreate,
      Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return genreService.createGenre(genreCreate, securityContext);
  }
}

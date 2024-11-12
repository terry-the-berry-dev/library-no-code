package com.lighthouse.library.model.controller;

import com.lighthouse.library.model.model.LenderToBook;
import com.lighthouse.library.model.request.LenderToBookCreate;
import com.lighthouse.library.model.request.LenderToBookFilter;
import com.lighthouse.library.model.request.LenderToBookUpdate;
import com.lighthouse.library.model.response.PaginationResponse;
import com.lighthouse.library.model.security.UserSecurityContext;
import com.lighthouse.library.model.service.LenderToBookService;
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
@RequestMapping("LenderToBook")
@Tag(name = "LenderToBook")
public class LenderToBookController {

  @Autowired private LenderToBookService lenderToBookService;

  @DeleteMapping("{id}")
  @Operation(summary = "deleteLenderToBook", description = "Deletes LenderToBook")
  public LenderToBook deleteLenderToBook(
      @PathVariable("id") String id, Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return lenderToBookService.deleteLenderToBook(id, securityContext);
  }

  @PostMapping("getAllLenderToBooks")
  @Operation(summary = "getAllLenderToBooks", description = "lists LenderToBooks")
  public PaginationResponse<LenderToBook> getAllLenderToBooks(
      @Valid @RequestBody LenderToBookFilter lenderToBookFilter, Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return lenderToBookService.getAllLenderToBooks(lenderToBookFilter, securityContext);
  }

  @PutMapping("updateLenderToBook")
  @Operation(summary = "updateLenderToBook", description = "Updates LenderToBook")
  public LenderToBook updateLenderToBook(
      @Validated(Update.class) @RequestBody LenderToBookUpdate lenderToBookUpdate,
      Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return lenderToBookService.updateLenderToBook(lenderToBookUpdate, securityContext);
  }

  @PostMapping("createLenderToBook")
  @Operation(summary = "createLenderToBook", description = "Creates LenderToBook")
  public LenderToBook createLenderToBook(
      @Validated(Create.class) @RequestBody LenderToBookCreate lenderToBookCreate,
      Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return lenderToBookService.createLenderToBook(lenderToBookCreate, securityContext);
  }
}

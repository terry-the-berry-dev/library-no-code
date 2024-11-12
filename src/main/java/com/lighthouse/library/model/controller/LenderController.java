package com.lighthouse.library.model.controller;

import com.lighthouse.library.model.model.Lender;
import com.lighthouse.library.model.request.LenderCreate;
import com.lighthouse.library.model.request.LenderFilter;
import com.lighthouse.library.model.request.LenderUpdate;
import com.lighthouse.library.model.response.PaginationResponse;
import com.lighthouse.library.model.security.UserSecurityContext;
import com.lighthouse.library.model.service.LenderService;
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
@RequestMapping("Lender")
@Tag(name = "Lender")
public class LenderController {

  @Autowired private LenderService lenderService;

  @DeleteMapping("{id}")
  @Operation(summary = "deleteLender", description = "Deletes Lender")
  public Lender deleteLender(@PathVariable("id") String id, Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return lenderService.deleteLender(id, securityContext);
  }

  @PostMapping("getAllLenders")
  @Operation(summary = "getAllLenders", description = "lists Lenders")
  public PaginationResponse<Lender> getAllLenders(
      @Valid @RequestBody LenderFilter lenderFilter, Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return lenderService.getAllLenders(lenderFilter, securityContext);
  }

  @PutMapping("updateLender")
  @Operation(summary = "updateLender", description = "Updates Lender")
  public Lender updateLender(
      @Validated(Update.class) @RequestBody LenderUpdate lenderUpdate,
      Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return lenderService.updateLender(lenderUpdate, securityContext);
  }

  @PostMapping("createLender")
  @Operation(summary = "createLender", description = "Creates Lender")
  public Lender createLender(
      @Validated(Create.class) @RequestBody LenderCreate lenderCreate,
      Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return lenderService.createLender(lenderCreate, securityContext);
  }
}

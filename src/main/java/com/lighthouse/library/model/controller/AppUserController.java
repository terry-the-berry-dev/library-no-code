package com.lighthouse.library.model.controller;

import com.lighthouse.library.model.model.AppUser;
import com.lighthouse.library.model.request.AppUserCreate;
import com.lighthouse.library.model.request.AppUserFilter;
import com.lighthouse.library.model.request.AppUserUpdate;
import com.lighthouse.library.model.response.PaginationResponse;
import com.lighthouse.library.model.security.UserSecurityContext;
import com.lighthouse.library.model.service.AppUserService;
import com.lighthouse.library.model.validation.Create;
import com.lighthouse.library.model.validation.Update;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("AppUser")
@Tag(name = "AppUser")
public class AppUserController {

  @Autowired private AppUserService appUserService;

  @DeleteMapping("{id}")
  @Operation(summary = "deleteAppUser", description = "Deletes AppUser")
  public AppUser deleteAppUser(@PathVariable("id") String id, Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return appUserService.deleteAppUser(id, securityContext);
  }

  @PostMapping("getAllAppUsers")
  @Operation(summary = "getAllAppUsers", description = "lists AppUsers")
  public PaginationResponse<AppUser> getAllAppUsers(
      @Valid @RequestBody AppUserFilter appUserFilter, Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return appUserService.getAllAppUsers(appUserFilter, securityContext);
  }

  @PutMapping("updateAppUser")
  @Operation(summary = "updateAppUser", description = "Updates AppUser")
  public AppUser updateAppUser(
      @Validated(Update.class) @RequestBody AppUserUpdate appUserUpdate,
      Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return appUserService.updateAppUser(appUserUpdate, securityContext);
  }

  @PostMapping("createAppUser")
  @Operation(summary = "createAppUser", description = "Creates AppUser")
  @PreAuthorize("hasAnyRole('User')")
  public AppUser createAppUser(
      @Validated(Create.class) @RequestBody AppUserCreate appUserCreate,
      Authentication authentication) {
    UserSecurityContext securityContext = (UserSecurityContext) authentication.getPrincipal();

    return appUserService.createAppUser(appUserCreate, securityContext);
  }
}

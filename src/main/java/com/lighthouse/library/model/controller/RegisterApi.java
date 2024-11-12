package com.lighthouse.library.model.controller;

import com.lighthouse.library.model.model.AppUser;
import com.lighthouse.library.model.request.AppUserCreate;
import com.lighthouse.library.model.request.AppUserFilter;
import com.lighthouse.library.model.security.Roles;
import com.lighthouse.library.model.service.AppUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = "RegisterApi")
@RestController
public class RegisterApi {

  private final AppUserService appUserService;

  public RegisterApi(AppUserService appUserService) {
    this.appUserService = appUserService;
  }

  @SecurityRequirements
  @PostMapping("register")
  public AppUser register(
      @RequestBody
          @org.springframework.validation.annotation.Validated(
              com.lighthouse.library.model.validation.Create.class)
          AppUserCreate appUserCreate) {
    appUserService
        .listAllAppUsers(
            new AppUserFilter().setUsername(Collections.singleton(appUserCreate.getUsername())),
            null)
        .stream()
        .findAny()
        .ifPresent(
            f -> {
              throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username already exists");
            });
    AppUser appUser =
        appUserService.createAppUser(appUserCreate.setPassword(Roles.User.name()), null);
    return appUser;
  }
}

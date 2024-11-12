package com.lighthouse.library.model.controller;

import com.lighthouse.library.model.AppInit;
import com.lighthouse.library.model.model.AppUser;
import com.lighthouse.library.model.request.AppUserCreate;
import com.lighthouse.library.model.request.AppUserFilter;
import com.lighthouse.library.model.request.AppUserUpdate;
import com.lighthouse.library.model.request.LoginRequest;
import com.lighthouse.library.model.response.PaginationResponse;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.*;
import org.testcontainers.containers.PostgreSQLContainer;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AppInit.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(
    replace = AutoConfigureTestDatabase.Replace.NONE) // deactivate the default behaviour
public class AppUserControllerTest {

  private AppUser testAppUser;

  @Autowired private TestRestTemplate restTemplate;

  private static final PostgreSQLContainer postgresqlContainer =
      new PostgreSQLContainer("postgres:15")
          .withDatabaseName("flexicore-test")
          .withUsername("flexicore")
          .withPassword("flexicore");

  static {
    postgresqlContainer.start();
  }

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresqlContainer::getUsername);
    registry.add("spring.datasource.password", postgresqlContainer::getPassword);
  }

  @BeforeAll
  public void init() {
    ResponseEntity<Object> authenticationResponse =
        this.restTemplate.postForEntity(
            "/login",
            new LoginRequest().setUsername("admin@flexicore.com").setPassword("admin"),
            Object.class);

    String authenticationKey =
        authenticationResponse.getHeaders().get(HttpHeaders.AUTHORIZATION).stream()
            .findFirst()
            .orElse(null);
    restTemplate
        .getRestTemplate()
        .setInterceptors(
            Collections.singletonList(
                (request, body, execution) -> {
                  request.getHeaders().add("Authorization", "Bearer " + authenticationKey);
                  return execution.execute(request, body);
                }));
  }

  @Test
  @Order(1)
  public void testAppUserCreate() {
    AppUserCreate request = new AppUserCreate();

    request.setUsername("test-string");

    ResponseEntity<AppUser> response =
        this.restTemplate.postForEntity("/AppUser/createAppUser", request, AppUser.class);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    testAppUser = response.getBody();
    assertAppUser(request, testAppUser);
  }

  @Test
  @Order(2)
  public void testListAllAppUsers() {
    AppUserFilter request = new AppUserFilter();
    ParameterizedTypeReference<PaginationResponse<AppUser>> t =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<AppUser>> response =
        this.restTemplate.exchange(
            "/AppUser/getAllAppUsers", HttpMethod.POST, new HttpEntity<>(request), t);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<AppUser> body = response.getBody();
    Assertions.assertNotNull(body);
    List<AppUser> AppUsers = body.getList();
    Assertions.assertNotEquals(0, AppUsers.size());
    Assertions.assertTrue(AppUsers.stream().anyMatch(f -> f.getId().equals(testAppUser.getId())));
  }

  public void assertAppUser(AppUserCreate request, AppUser testAppUser) {
    Assertions.assertNotNull(testAppUser);
    if (request.getUsername() != null) {
      Assertions.assertEquals(request.getUsername(), testAppUser.getUsername());
    }
    if (request.getPassword() != null) {
      Assertions.assertEquals(request.getPassword(), testAppUser.getPassword());
    }
    if (request.getRoles() != null) {
      Assertions.assertEquals(request.getRoles(), testAppUser.getRoles());
    }
  }

  @Test
  @Order(3)
  public void testAppUserUpdate() {
    AppUserUpdate request = new AppUserUpdate().setId(testAppUser.getId());

    ResponseEntity<AppUser> response =
        this.restTemplate.exchange(
            "/AppUser/updateAppUser", HttpMethod.PUT, new HttpEntity<>(request), AppUser.class);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    testAppUser = response.getBody();
    assertAppUser(request, testAppUser);
  }
}

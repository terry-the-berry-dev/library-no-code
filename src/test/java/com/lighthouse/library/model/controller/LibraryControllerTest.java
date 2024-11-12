package com.lighthouse.library.model.controller;

import com.lighthouse.library.model.AppInit;
import com.lighthouse.library.model.model.Library;
import com.lighthouse.library.model.request.LibraryCreate;
import com.lighthouse.library.model.request.LibraryFilter;
import com.lighthouse.library.model.request.LibraryUpdate;
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
public class LibraryControllerTest {

  private Library testLibrary;

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
  public void testLibraryCreate() {
    LibraryCreate request = new LibraryCreate();

    request.setName("test-string");
    request.setDeleted(true);

    ResponseEntity<Library> response =
        this.restTemplate.postForEntity("/Library/createLibrary", request, Library.class);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    testLibrary = response.getBody();
    assertLibrary(request, testLibrary);
  }

  @Test
  @Order(2)
  public void testListAllLibraries() {
    LibraryFilter request = new LibraryFilter();
    ParameterizedTypeReference<PaginationResponse<Library>> t =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<Library>> response =
        this.restTemplate.exchange(
            "/Library/getAllLibraries", HttpMethod.POST, new HttpEntity<>(request), t);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<Library> body = response.getBody();
    Assertions.assertNotNull(body);
    List<Library> Libraries = body.getList();
    Assertions.assertNotEquals(0, Libraries.size());
    Assertions.assertTrue(Libraries.stream().anyMatch(f -> f.getId().equals(testLibrary.getId())));
  }

  public void assertLibrary(LibraryCreate request, Library testLibrary) {
    Assertions.assertNotNull(testLibrary);
    if (request.getName() != null) {
      Assertions.assertEquals(request.getName(), testLibrary.getName());
    }
    if (request.getDeleted() != null) {
      Assertions.assertEquals(request.getDeleted(), testLibrary.isDeleted());
    }
  }

  @Test
  @Order(3)
  public void testLibraryUpdate() {
    LibraryUpdate request = new LibraryUpdate().setId(testLibrary.getId());

    ResponseEntity<Library> response =
        this.restTemplate.exchange(
            "/Library/updateLibrary", HttpMethod.PUT, new HttpEntity<>(request), Library.class);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    testLibrary = response.getBody();
    assertLibrary(request, testLibrary);
  }
}

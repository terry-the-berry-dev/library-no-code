package com.lighthouse.library.model.controller;

import com.lighthouse.library.model.AppInit;
import com.lighthouse.library.model.model.Author;
import com.lighthouse.library.model.request.AuthorCreate;
import com.lighthouse.library.model.request.AuthorFilter;
import com.lighthouse.library.model.request.AuthorUpdate;
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
public class AuthorControllerTest {

  private Author testAuthor;

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
  public void testAuthorCreate() {
    AuthorCreate request = new AuthorCreate();

    request.setDeleted("test-string");
    request.setName("test-string");

    ResponseEntity<Author> response =
        this.restTemplate.postForEntity("/Author/createAuthor", request, Author.class);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    testAuthor = response.getBody();
    assertAuthor(request, testAuthor);
  }

  @Test
  @Order(2)
  public void testListAllAuthors() {
    AuthorFilter request = new AuthorFilter();
    ParameterizedTypeReference<PaginationResponse<Author>> t =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<Author>> response =
        this.restTemplate.exchange(
            "/Author/getAllAuthors", HttpMethod.POST, new HttpEntity<>(request), t);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<Author> body = response.getBody();
    Assertions.assertNotNull(body);
    List<Author> Authors = body.getList();
    Assertions.assertNotEquals(0, Authors.size());
    Assertions.assertTrue(Authors.stream().anyMatch(f -> f.getId().equals(testAuthor.getId())));
  }

  public void assertAuthor(AuthorCreate request, Author testAuthor) {
    Assertions.assertNotNull(testAuthor);
    if (request.getDeleted() != null) {
      Assertions.assertEquals(request.getDeleted(), testAuthor.getDeleted());
    }
    if (request.getName() != null) {
      Assertions.assertEquals(request.getName(), testAuthor.getName());
    }
  }

  @Test
  @Order(3)
  public void testAuthorUpdate() {
    AuthorUpdate request = new AuthorUpdate().setId(testAuthor.getId());

    ResponseEntity<Author> response =
        this.restTemplate.exchange(
            "/Author/updateAuthor", HttpMethod.PUT, new HttpEntity<>(request), Author.class);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    testAuthor = response.getBody();
    assertAuthor(request, testAuthor);
  }
}

package com.lighthouse.library.model.controller;

import com.lighthouse.library.model.AppInit;
import com.lighthouse.library.model.model.Book;
import com.lighthouse.library.model.model.Library;
import com.lighthouse.library.model.model.LibraryToBook;
import com.lighthouse.library.model.request.LibraryToBookCreate;
import com.lighthouse.library.model.request.LibraryToBookFilter;
import com.lighthouse.library.model.request.LibraryToBookUpdate;
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
public class LibraryToBookControllerTest {

  private LibraryToBook testLibraryToBook;

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

  @Autowired private Book book;
  @Autowired private Library library;

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
  public void testLibraryToBookCreate() {
    LibraryToBookCreate request = new LibraryToBookCreate();

    request.setBookId(this.book.getId());
    request.setLibraryId(this.library.getId());

    ResponseEntity<LibraryToBook> response =
        this.restTemplate.postForEntity(
            "/LibraryToBook/createLibraryToBook", request, LibraryToBook.class);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    testLibraryToBook = response.getBody();
    assertLibraryToBook(request, testLibraryToBook);
  }

  @Test
  @Order(2)
  public void testListAllLibraryToBooks() {
    LibraryToBookFilter request = new LibraryToBookFilter();
    ParameterizedTypeReference<PaginationResponse<LibraryToBook>> t =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<LibraryToBook>> response =
        this.restTemplate.exchange(
            "/LibraryToBook/getAllLibraryToBooks", HttpMethod.POST, new HttpEntity<>(request), t);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<LibraryToBook> body = response.getBody();
    Assertions.assertNotNull(body);
    List<LibraryToBook> LibraryToBooks = body.getList();
    Assertions.assertNotEquals(0, LibraryToBooks.size());
    Assertions.assertTrue(
        LibraryToBooks.stream().anyMatch(f -> f.getId().equals(testLibraryToBook.getId())));
  }

  public void assertLibraryToBook(LibraryToBookCreate request, LibraryToBook testLibraryToBook) {
    Assertions.assertNotNull(testLibraryToBook);
    if (request.getBookId() != null) {
      Assertions.assertNotNull(testLibraryToBook.getBook());
      Assertions.assertEquals(request.getBookId(), testLibraryToBook.getBook().getId());
    }
    if (request.getLibraryId() != null) {
      Assertions.assertNotNull(testLibraryToBook.getLibrary());
      Assertions.assertEquals(request.getLibraryId(), testLibraryToBook.getLibrary().getId());
    }
  }

  @Test
  @Order(3)
  public void testLibraryToBookUpdate() {
    LibraryToBookUpdate request = new LibraryToBookUpdate().setId(testLibraryToBook.getId());

    ResponseEntity<LibraryToBook> response =
        this.restTemplate.exchange(
            "/LibraryToBook/updateLibraryToBook",
            HttpMethod.PUT,
            new HttpEntity<>(request),
            LibraryToBook.class);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    testLibraryToBook = response.getBody();
    assertLibraryToBook(request, testLibraryToBook);
  }
}

package com.lighthouse.library.model.controller;

import com.lighthouse.library.model.AppInit;
import com.lighthouse.library.model.model.Book;
import com.lighthouse.library.model.model.Lender;
import com.lighthouse.library.model.model.LenderToBook;
import com.lighthouse.library.model.request.LenderToBookCreate;
import com.lighthouse.library.model.request.LenderToBookFilter;
import com.lighthouse.library.model.request.LenderToBookUpdate;
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
public class LenderToBookControllerTest {

  private LenderToBook testLenderToBook;

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
  @Autowired private Lender lender;

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
  public void testLenderToBookCreate() {
    LenderToBookCreate request = new LenderToBookCreate();

    request.setLenderId(this.lender.getId());
    request.setBookId(this.book.getId());

    ResponseEntity<LenderToBook> response =
        this.restTemplate.postForEntity(
            "/LenderToBook/createLenderToBook", request, LenderToBook.class);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    testLenderToBook = response.getBody();
    assertLenderToBook(request, testLenderToBook);
  }

  @Test
  @Order(2)
  public void testListAllLenderToBooks() {
    LenderToBookFilter request = new LenderToBookFilter();
    ParameterizedTypeReference<PaginationResponse<LenderToBook>> t =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<LenderToBook>> response =
        this.restTemplate.exchange(
            "/LenderToBook/getAllLenderToBooks", HttpMethod.POST, new HttpEntity<>(request), t);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<LenderToBook> body = response.getBody();
    Assertions.assertNotNull(body);
    List<LenderToBook> LenderToBooks = body.getList();
    Assertions.assertNotEquals(0, LenderToBooks.size());
    Assertions.assertTrue(
        LenderToBooks.stream().anyMatch(f -> f.getId().equals(testLenderToBook.getId())));
  }

  public void assertLenderToBook(LenderToBookCreate request, LenderToBook testLenderToBook) {
    Assertions.assertNotNull(testLenderToBook);
    if (request.getLenderId() != null) {
      Assertions.assertNotNull(testLenderToBook.getLender());
      Assertions.assertEquals(request.getLenderId(), testLenderToBook.getLender().getId());
    }
    if (request.getBookId() != null) {
      Assertions.assertNotNull(testLenderToBook.getBook());
      Assertions.assertEquals(request.getBookId(), testLenderToBook.getBook().getId());
    }
  }

  @Test
  @Order(3)
  public void testLenderToBookUpdate() {
    LenderToBookUpdate request = new LenderToBookUpdate().setId(testLenderToBook.getId());

    ResponseEntity<LenderToBook> response =
        this.restTemplate.exchange(
            "/LenderToBook/updateLenderToBook",
            HttpMethod.PUT,
            new HttpEntity<>(request),
            LenderToBook.class);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    testLenderToBook = response.getBody();
    assertLenderToBook(request, testLenderToBook);
  }
}

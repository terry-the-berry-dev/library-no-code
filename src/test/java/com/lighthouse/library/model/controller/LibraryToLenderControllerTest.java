package com.lighthouse.library.model.controller;

import com.lighthouse.library.model.AppInit;
import com.lighthouse.library.model.model.Lender;
import com.lighthouse.library.model.model.Library;
import com.lighthouse.library.model.model.LibraryToLender;
import com.lighthouse.library.model.request.LibraryToLenderCreate;
import com.lighthouse.library.model.request.LibraryToLenderFilter;
import com.lighthouse.library.model.request.LibraryToLenderUpdate;
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
public class LibraryToLenderControllerTest {

  private LibraryToLender testLibraryToLender;

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

  @Autowired private Library library;
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
  public void testLibraryToLenderCreate() {
    LibraryToLenderCreate request = new LibraryToLenderCreate();

    request.setLibraryId(this.library.getId());
    request.setLenderId(this.lender.getId());

    ResponseEntity<LibraryToLender> response =
        this.restTemplate.postForEntity(
            "/LibraryToLender/createLibraryToLender", request, LibraryToLender.class);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    testLibraryToLender = response.getBody();
    assertLibraryToLender(request, testLibraryToLender);
  }

  @Test
  @Order(2)
  public void testListAllLibraryToLenders() {
    LibraryToLenderFilter request = new LibraryToLenderFilter();
    ParameterizedTypeReference<PaginationResponse<LibraryToLender>> t =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<LibraryToLender>> response =
        this.restTemplate.exchange(
            "/LibraryToLender/getAllLibraryToLenders",
            HttpMethod.POST,
            new HttpEntity<>(request),
            t);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<LibraryToLender> body = response.getBody();
    Assertions.assertNotNull(body);
    List<LibraryToLender> LibraryToLenders = body.getList();
    Assertions.assertNotEquals(0, LibraryToLenders.size());
    Assertions.assertTrue(
        LibraryToLenders.stream().anyMatch(f -> f.getId().equals(testLibraryToLender.getId())));
  }

  public void assertLibraryToLender(
      LibraryToLenderCreate request, LibraryToLender testLibraryToLender) {
    Assertions.assertNotNull(testLibraryToLender);
    if (request.getLibraryId() != null) {
      Assertions.assertNotNull(testLibraryToLender.getLibrary());
      Assertions.assertEquals(request.getLibraryId(), testLibraryToLender.getLibrary().getId());
    }
    if (request.getLenderId() != null) {
      Assertions.assertNotNull(testLibraryToLender.getLender());
      Assertions.assertEquals(request.getLenderId(), testLibraryToLender.getLender().getId());
    }
  }

  @Test
  @Order(3)
  public void testLibraryToLenderUpdate() {
    LibraryToLenderUpdate request = new LibraryToLenderUpdate().setId(testLibraryToLender.getId());

    ResponseEntity<LibraryToLender> response =
        this.restTemplate.exchange(
            "/LibraryToLender/updateLibraryToLender",
            HttpMethod.PUT,
            new HttpEntity<>(request),
            LibraryToLender.class);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    testLibraryToLender = response.getBody();
    assertLibraryToLender(request, testLibraryToLender);
  }
}

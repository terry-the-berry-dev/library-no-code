package com.lighthouse.library.model.controller;

import com.lighthouse.library.model.AppInit;
import com.lighthouse.library.model.model.Lender;
import com.lighthouse.library.model.request.LenderCreate;
import com.lighthouse.library.model.request.LenderFilter;
import com.lighthouse.library.model.request.LenderUpdate;
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
public class LenderControllerTest {

  private Lender testLender;

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
  public void testLenderCreate() {
    LenderCreate request = new LenderCreate();

    request.setDeleted(true);
    request.setName("test-string");

    ResponseEntity<Lender> response =
        this.restTemplate.postForEntity("/Lender/createLender", request, Lender.class);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    testLender = response.getBody();
    assertLender(request, testLender);
  }

  @Test
  @Order(2)
  public void testListAllLenders() {
    LenderFilter request = new LenderFilter();
    ParameterizedTypeReference<PaginationResponse<Lender>> t =
        new ParameterizedTypeReference<>() {};

    ResponseEntity<PaginationResponse<Lender>> response =
        this.restTemplate.exchange(
            "/Lender/getAllLenders", HttpMethod.POST, new HttpEntity<>(request), t);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    PaginationResponse<Lender> body = response.getBody();
    Assertions.assertNotNull(body);
    List<Lender> Lenders = body.getList();
    Assertions.assertNotEquals(0, Lenders.size());
    Assertions.assertTrue(Lenders.stream().anyMatch(f -> f.getId().equals(testLender.getId())));
  }

  public void assertLender(LenderCreate request, Lender testLender) {
    Assertions.assertNotNull(testLender);
    if (request.getDeleted() != null) {
      Assertions.assertEquals(request.getDeleted(), testLender.isDeleted());
    }
    if (request.getName() != null) {
      Assertions.assertEquals(request.getName(), testLender.getName());
    }
  }

  @Test
  @Order(3)
  public void testLenderUpdate() {
    LenderUpdate request = new LenderUpdate().setId(testLender.getId());

    ResponseEntity<Lender> response =
        this.restTemplate.exchange(
            "/Lender/updateLender", HttpMethod.PUT, new HttpEntity<>(request), Lender.class);

    Assertions.assertEquals(200, response.getStatusCodeValue());
    testLender = response.getBody();
    assertLender(request, testLender);
  }
}

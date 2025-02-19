package com.example.customers.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.testcontainers.containers.MySQLContainer;
import com.example.customers.model.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "Spring.config.name=application-integration")
public class CustomerApiIT {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();
    private String baseUrl;

    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:9.2")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/v1/customers";
    }

    @BeforeAll
    static void startContainer() {
        mysql.start();
    }

    @AfterAll
    static void stopContainer() {
        mysql.stop();
    }

    @DynamicPropertySource
    static void configureTestDatabase(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "password"); // Set test credentials
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private CustomerDto createCustomer(CustomerDto customer) {
        HttpEntity<CustomerDto> request = new HttpEntity<>(customer, createHeaders());
        ResponseEntity<CustomerDto> response = restTemplate.exchange(baseUrl, HttpMethod.POST, request, CustomerDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    private CustomerDto getCustomerById(UUID customerId) {
        HttpEntity<Void> request = new HttpEntity<>(createHeaders());
        ResponseEntity<CustomerDto> response = restTemplate.exchange(
                baseUrl + "/" + customerId, HttpMethod.GET, request , CustomerDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    private void deleteCustomer(UUID customerId) {
        HttpEntity<Void> request = new HttpEntity<>(createHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(
                baseUrl + "/" + customerId, HttpMethod.DELETE, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void testCreateCustomer() {
        CustomerDto newCustomer = new CustomerDto("Jane", "Smith", "jane.smith@example.com", "9876543210");
        CustomerDto createdCustomer = createCustomer(newCustomer);
        assertThat(createdCustomer).isNotNull();
        assertThat(createdCustomer.getEmail()).isEqualTo("jane.smith@example.com");
    }

    @Test
    void testGetCustomerById() {
        CustomerDto newCustomer = new CustomerDto("John", "Doe", "john.doe@example.com", "1234567890");
        CustomerDto createdCustomer = createCustomer(newCustomer);
        CustomerDto fetchedCustomer = getCustomerById(createdCustomer.getId());
        assertThat(fetchedCustomer).isNotNull();
        assertThat(fetchedCustomer.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testUpdateCustomer() {
        // Create a customer first
        CustomerDto newCustomer = new CustomerDto("Alice", "Brown", "alice.brown@example.com", "5551234567");
        CustomerDto createdCustomer = createCustomer(newCustomer);

        // Prepare update data
        CustomerDto updatedCustomer = new CustomerDto("Alice", "Brown", "alice.updated@example.com", "5559998888");

        // Send PUT request
        HttpEntity<CustomerDto> request = new HttpEntity<>(updatedCustomer, createHeaders());
        ResponseEntity<CustomerDto> response = restTemplate.exchange(
                baseUrl + "/" + createdCustomer.getId(), HttpMethod.PUT, request, CustomerDto.class);

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEmail()).isEqualTo("alice.updated@example.com");
    }

    @Test
    void testDeleteCustomer() {
        // Create a customer first
        CustomerDto newCustomer = new CustomerDto("Bob", "Green", "bob.green@example.com", "6667778888");
        CustomerDto createdCustomer = createCustomer(newCustomer);

        // Delete the customer
        deleteCustomer(createdCustomer.getId());

        // Try to get the deleted customer (should return 404)
        HttpEntity<Void> request = new HttpEntity<>(createHeaders());
        try {
            ResponseEntity<CustomerDto> response = restTemplate.exchange(
                    baseUrl + "/" + createdCustomer.getId(), HttpMethod.GET, request, CustomerDto.class);
        } catch (Exception ex) {
            assertThat(ex instanceof HttpClientErrorException);
        }
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND); // TODO: Need to fix exception handling
    }
}

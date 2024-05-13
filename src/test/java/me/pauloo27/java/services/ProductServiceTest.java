package me.pauloo27.java.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.DriverManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import me.pauloo27.java.db.DB;
import me.pauloo27.java.db.Schema;
import me.pauloo27.java.utils.AppException;

@Testcontainers
public class ProductServiceTest {
  @Container
  private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
      .withDatabaseName("testdb")
      .withUsername("test")
      .withPassword("test");

  private ProductService underTest;

  @BeforeEach
  public void setUp() throws Exception {
    var conn = DriverManager.getConnection(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
    DB.setConnection(conn);
    Schema.createTables(conn);
    Schema.truncateTables(conn);
    this.underTest = new ProductService();
  }

  @Test
  public void shouldNotAllowEmptyProductName() {
    var thrown = assertThrows(AppException.class, () -> this.underTest.create("", 100.0, 10));
    assertEquals("Nome do produto não pode ser vazio", thrown.getMessage());
  }

  @Test
  public void shouldNotAllowZeroOrNegativePrice() {
    var thrown = assertThrows(AppException.class, () -> this.underTest.create("Test Product", 0, 10));
    assertEquals("Preço do produto não pode ser menor ou igual a zero", thrown.getMessage());
  }

  @Test
  public void shouldNotAllowNegativeQuantity() {
    var thrown = assertThrows(AppException.class, () -> this.underTest.create("Test Product", 100.0, -1));
    assertEquals("Quantidade do produto não pode ser menor que zero", thrown.getMessage());
  }

  @Test
  public void shouldCreateProduct() {
    assertDoesNotThrow(() -> this.underTest.create("Valid Product", 100.0, 10));
  }

  @Test
  public void shouldReturnEmptyCollectionIfNoProducts() throws Exception {
    var products = underTest.findAll();
    assertTrue(products.isEmpty());
  }

  @Test
  public void shouldFindAllProducts() throws Exception {
    // Create some test products
    this.underTest.create("Product 1", 10.00, 5);
    this.underTest.create("Product 2", 20.00, 3);

    var products = underTest.findAll();
    assertNotNull(products);
    assertFalse(products.isEmpty());
    assertEquals(2, products.size());

    assertEquals("Product 1", products.stream().findFirst().get().getName());
    assertEquals(10.00, products.stream().findFirst().get().getPrice());
    assertEquals(5, products.stream().findFirst().get().getAmount());

    assertEquals("Product 2", products.stream().skip(1).findFirst().get().getName());
    assertEquals(20.00, products.stream().skip(1).findFirst().get().getPrice());
    assertEquals(3, products.stream().skip(1).findFirst().get().getAmount());
  }

  @Test
  public void shouldSearchProducts() throws Exception {
    // Create some test products
    this.underTest.create("Product 1", 10.00, 5);
    this.underTest.create("Product 2", 20.00, 3);

    var products = underTest.search("Product 1");
    assertNotNull(products);
    assertFalse(products.isEmpty());
    assertEquals(1, products.size());

    assertEquals("Product 1", products.stream().findFirst().get().getName());
    assertEquals(10.00, products.stream().findFirst().get().getPrice());
    assertEquals(5, products.stream().findFirst().get().getAmount());

    products = underTest.search("Product");

    assertNotNull(products);
    assertFalse(products.isEmpty());
    assertEquals(2, products.size());

    assertEquals("Product 1", products.stream().findFirst().get().getName());
    assertEquals(10.00, products.stream().findFirst().get().getPrice());
    assertEquals(5, products.stream().findFirst().get().getAmount());

    assertEquals("Product 2", products.stream().skip(1).findFirst().get().getName());
    assertEquals(20.00, products.stream().skip(1).findFirst().get().getPrice());
    assertEquals(3, products.stream().skip(1).findFirst().get().getAmount());
  }
}

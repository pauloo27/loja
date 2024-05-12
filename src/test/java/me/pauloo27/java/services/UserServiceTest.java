package me.pauloo27.java.services;

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
public class UserServiceTest {
  @Container
  private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
      .withDatabaseName("testdb")
      .withUsername("test")
      .withPassword("test");

  private UserService underTest;

  @BeforeEach
  public void setUp() throws Exception {
    var conn = DriverManager.getConnection(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
    DB.setConnection(conn);
    Schema.createTables(conn);
    this.underTest = new UserService();
  }

  @Test
  public void shouldNotAllowEmptyValues() {
    var thrown = assertThrows(AppException.class, () -> this.underTest.register("", ""));
    assertEquals("Preencha todos os campos", thrown.getMessage());
  }

  @Test
  public void shouldNotAllowShortPasswords() {
    var thrown = assertThrows(AppException.class, () -> this.underTest.register("test", "123"));
    assertEquals("A senha deve ter no mínimo 6 caracteres", thrown.getMessage());
  }

  @Test
  public void shouldRegisterUser() {
    assertDoesNotThrow(() -> this.underTest.register("test", "123456"));
  }

  @Test
  public void shouldNotAllowDuplicateUsernames() {
    assertDoesNotThrow(() -> this.underTest.register("admin", "123456"));
    var thrown = assertThrows(AppException.class, () -> this.underTest.register("admin", "123456"));

    assertEquals("Nome de usuário já em uso", thrown.getMessage());
  }

  @Test
  public void shouldNotAllowEmptyValuesOnLogin() {
    var thrown = assertThrows(AppException.class, () -> this.underTest.login("", ""));
    assertEquals("Preencha todos os campos", thrown.getMessage());
  }

  @Test
  public void shouldNotAllowInvalidCredentials() {
    assertDoesNotThrow(() -> this.underTest.register("johndoe0", "123456"));
    var thrown = assertThrows(AppException.class, () -> this.underTest.login("johndoe", "1234567"));

    assertEquals("Usuário ou senha inválidos", thrown.getMessage());
  }

  @Test
  public void shouldNotAllowInvalidUsername() {
    var thrown = assertThrows(AppException.class, () -> this.underTest.login("notregistered", "123456"));

    assertEquals("Usuário ou senha inválidos", thrown.getMessage());
  }

  @Test
  public void shouldLogin() {
    assertDoesNotThrow(() -> this.underTest.register("johndoe1", "123456"));
    assertDoesNotThrow(() -> this.underTest.login("johndoe1", "123456"));
  }
}

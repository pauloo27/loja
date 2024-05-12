package me.pauloo27.java.db.repos;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import me.pauloo27.java.db.DB;
import me.pauloo27.java.db.models.User;

public class UserRepository {

    public void create(String username, String password) throws NoSuchAlgorithmException, SQLException {
        var connection = DB.getConnection();
        var sql = "INSERT INTO public.user (username, password) VALUES (?, ?)";

        var digest = MessageDigest.getInstance("SHA-256");
        var hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        var hashedPassword = String.format("%064x", new BigInteger(1, hash));

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    public User findByUsernameAndPassword(String username, String password)
            throws NoSuchAlgorithmException, SQLException {
        var connection = DB.getConnection();
        var sql = "SELECT id, username FROM public.user WHERE username = ? AND password = ?";

        var digest = MessageDigest.getInstance("SHA-256");
        var hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        var hashedPassword = String.format("%064x", new BigInteger(1, hash));

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getInt("id"), resultSet.getString("username"));
            }
            return null;
        } catch (SQLException e) {
            throw e;
        }
    }
}

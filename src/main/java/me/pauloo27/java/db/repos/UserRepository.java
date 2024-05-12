package me.pauloo27.java.db.repos;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import me.pauloo27.java.db.DB;

public class UserRepository {

    public void register(String username, String password) throws NoSuchAlgorithmException, SQLException {
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
}

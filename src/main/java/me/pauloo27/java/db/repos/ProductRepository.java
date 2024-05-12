package me.pauloo27.java.db.repos;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import me.pauloo27.java.db.DB;

public class ProductRepository {

    public void create(String name, double price, int amount) throws SQLException {
        var connection = DB.getConnection();
        var sql = "INSERT INTO public.product (name, price, amount) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, price);
            preparedStatement.setInt(3, amount);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
}

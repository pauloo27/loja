package me.pauloo27.java.db.repos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import me.pauloo27.java.db.DB;

import me.pauloo27.java.db.models.Product;

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

    public Collection<Product> findAll() throws SQLException {
        var connection = DB.getConnection();
        var sql = "SELECT * FROM public.product";

        try (var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();

            var products = new ArrayList<Product>();

            while (resultSet.next()) {
                var product = new Product(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getDouble("price"), resultSet.getInt("amount"));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw e;
        }
    }
}

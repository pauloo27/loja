package me.pauloo27.java.db.repos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import me.pauloo27.java.db.DB;

import me.pauloo27.java.db.models.Product;

public class ProductRepository {

    public Product create(String name, double price, int amount) throws SQLException {
        var connection = DB.getConnection();
        var sql = "INSERT INTO public.product (name, price, amount) VALUES (?, ?, ?) RETURNING *";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, price);
            preparedStatement.setInt(3, amount);
            var resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new SQLException("Erro ao criar produto");
            }
            return Product.fromResultSet(resultSet);
        } catch (SQLException e) {
            throw e;
        }
    }

    public Product update(Product product) {
        var connection = DB.getConnection();
        var sql = "UPDATE public.product SET name = ?, price = ?, amount = ? WHERE id = ? RETURNING *";

        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getAmount());
            preparedStatement.setInt(4, product.getId());
            var resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new SQLException("Erro ao atualizar produto");
            }
            return Product.fromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Collection<Product> findAll() throws SQLException {
        var connection = DB.getConnection();
        var sql = "SELECT * FROM public.product";

        try (var preparedStatement = connection.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();

            var products = new ArrayList<Product>();

            while (resultSet.next()) {
                var product = Product.fromResultSet(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean deleteByID(int id) throws SQLException {
        var connection = DB.getConnection();
        var sql = "DELETE FROM public.product WHERE id = ?";

        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}

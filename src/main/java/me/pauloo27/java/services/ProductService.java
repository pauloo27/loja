package me.pauloo27.java.services;

import java.sql.SQLException;

import me.pauloo27.java.db.repos.ProductRepository;
import me.pauloo27.java.utils.AppException;

public class ProductService {
    public void create(String name, double price, int amount) throws SQLException {
        if (name.isBlank()) {
            throw new AppException("Erro", "Nome do produto não pode ser vazio");
        }

        if (price <= 0) {
            throw new AppException("Erro", "Preço do produto não pode ser menor ou igual a zero");
        }

        if (amount < 0) {
            throw new AppException("Erro", "Quantidade do produto não pode ser menor que zero");
        }

        var repo = new ProductRepository();
        repo.create(name, price, amount);
    }
}

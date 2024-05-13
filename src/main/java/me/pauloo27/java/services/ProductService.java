package me.pauloo27.java.services;

import java.sql.SQLException;
import java.util.Collection;

import me.pauloo27.java.db.models.Product;
import me.pauloo27.java.db.repos.ProductRepository;
import me.pauloo27.java.utils.AppException;

public class ProductService {
    public Product create(String name, double price, int amount) throws AppException {
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
        try {
            return repo.create(name, price, amount);
        } catch (Exception e) {
            throw new AppException("Erro", "Algo deu errado ao criar produto");
        }
    }

    public Collection<Product> findAll() throws AppException {
        var repo = new ProductRepository();
        try {
            return repo.findAll();
        } catch (Exception e) {
            throw new AppException("Erro", "Erro ao buscar produtos");
        }
    }
}

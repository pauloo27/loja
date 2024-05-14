package me.pauloo27.java.view.scenes.listeners;

import me.pauloo27.java.db.models.Product;

public interface ProductMutationListener {
    void onProductCreated(Product product);
}

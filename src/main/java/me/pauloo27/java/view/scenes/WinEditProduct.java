package me.pauloo27.java.view.scenes;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import me.pauloo27.java.db.models.Product;
import me.pauloo27.java.services.ProductService;
import me.pauloo27.java.view.WinBase;
import me.pauloo27.java.view.scenes.listeners.ProductMutationListener;

public class WinEditProduct extends WinBase {
    private ProductService productService;
    private List<ProductMutationListener> listeners = new ArrayList<>();
    private Product product;

    public void addProductMutationListener(ProductMutationListener listener) {
        listeners.add(listener);
    }

    public void removeProductMutationListener(ProductMutationListener listener) {
        listeners.remove(listener);
    }

    private void notifyProductMudated(Product product) {
        for (ProductMutationListener listener : listeners) {
            listener.onProductCreated(product);
        }
    }

    public WinEditProduct(Product product) {
        super("Editar Produto", JFrame.DISPOSE_ON_CLOSE);
        this.product = product;
        this.productService = new ProductService();
        this.postSetupComponents();
    }

    public void postSetupComponents() {
        super.setupComponents();

        var lblId = new JLabel("ID:");
        super.addAt(lblId, 10, 50);

        var fieldId = new JTextField();
        fieldId.setEnabled(false);
        fieldId.setText(String.valueOf(this.product.getId()));
        super.addAt(fieldId, 10, 70, 200, 20);

        var lblName = new JLabel("Nome:");
        super.addAt(lblName, 10, 100);

        var fieldName = new JTextField();
        fieldName.setText(this.product.getName());
        super.addAt(fieldName, 10, 120, 200, 20);

        var lblPrice = new JLabel("Preço:");
        super.addAt(lblPrice, 10, 150);

        var modelPrice = new SpinnerNumberModel(0.0, 0.0, 1000.0, 0.1);

        var fieldPrice = new JSpinner(modelPrice);
        fieldPrice.setValue(this.product.getPrice());
        super.addAt(fieldPrice, 10, 170, 200, 20);

        var lblAmount = new JLabel("Quantidade:");
        super.addAt(lblAmount, 10, 200);

        var modelAmount = new SpinnerNumberModel(0, 0, 1000, 1);

        var fieldAmount = new JSpinner(modelAmount);
        fieldAmount.setValue(this.product.getAmount());
        super.addAt(fieldAmount, 10, 220, 200, 20);

        var btnNewProduct = new JButton("Salvar");
        super.addAt(btnNewProduct, 10, 260, 200, 20);

        btnNewProduct.addActionListener((a) -> {
            var id = this.product.getId();
            var name = fieldName.getText();
            var price = (double) fieldPrice.getValue();
            var amount = (int) fieldAmount.getValue();

            try {
                var product = this.productService.update(id, name, price, amount);
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                this.notifyProductMudated(product);
                this.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void dispose() {
        this.listeners.clear();
        super.dispose();
    }
}

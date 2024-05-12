package me.pauloo27.java.view.scenes;

import javax.swing.*;

import me.pauloo27.java.services.ProductService;
import me.pauloo27.java.view.WinBase;

public class WinNewProduct extends WinBase {
    private ProductService productService;

    public WinNewProduct() {
        super("Novo Produto", JFrame.DISPOSE_ON_CLOSE);
        this.productService = new ProductService();
    }

    public void setupComponents() {
        super.setupComponents();

        var lblName = new JLabel("Nome:");
        super.addAt(lblName, 10, 50);

        var fieldName = new JTextField();
        super.addAt(fieldName, 10, 70, 200, 20);

        var lblPrice = new JLabel("Preço:");
        super.addAt(lblPrice, 10, 100);

        var modelPrice = new SpinnerNumberModel(0.0, 0.0, 1000.0, 0.1);

        var fieldPrice = new JSpinner(modelPrice);
        super.addAt(fieldPrice, 10, 120, 200, 20);

        var lblAmount = new JLabel("Quantidade:");
        super.addAt(lblAmount, 10, 150);

        var modelAmount = new SpinnerNumberModel(0, 0, 1000, 1);
        var fieldAmount = new JSpinner(modelAmount);
        super.addAt(fieldAmount, 10, 170, 200, 20);

        var btnNewProduct = new JButton("Criar");
        super.addAt(btnNewProduct, 10, 200, 200, 20);

        btnNewProduct.addActionListener((a) -> {
            var name = fieldName.getText();
            var price = (double) fieldPrice.getValue();
            var amount = (int) fieldAmount.getValue();

            try {
                this.productService.create(name, price, amount);
                JOptionPane.showMessageDialog(this, "Produto criado com sucesso", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

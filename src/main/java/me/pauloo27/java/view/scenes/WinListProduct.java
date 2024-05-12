package me.pauloo27.java.view.scenes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import me.pauloo27.java.db.models.Product;
import me.pauloo27.java.services.ProductService;
import me.pauloo27.java.view.WinBase;

public class WinListProduct extends WinBase {
    private ProductService productService;

    public WinListProduct() {
        super("Listar Produtos");
        this.productService = new ProductService();
        this.postSetupComponents();
    }

    public void postSetupComponents() {
        String[] columns = { "Nome", "Quantidade", "Preço" };

        Collection<Product> products = null;
        try {
            products = this.productService.findAll();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar produtos", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        var data = new ArrayList<>();
        for (var product : products) {
            String[] dataProduct = { product.getName(), Integer.toString(product.getAmount()),
                    Double.toString(product.getPrice()) };
            data.add(dataProduct);
        }

        String[][] dataArr = {};
        JTable table = new JTable(data.toArray(dataArr), columns);
        JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        table.setEnabled(false);
        this.addAt(scroll, 10, 50, 480, 180);

        var btnNewProduct = new JButton("Novo Produto");
        this.addAt(btnNewProduct, 10, 240, 200, 20);

        btnNewProduct.addActionListener((a) -> {
            var newProductWin = new WinNewProduct();
            newProductWin.setVisible(true);
        });
    }
}

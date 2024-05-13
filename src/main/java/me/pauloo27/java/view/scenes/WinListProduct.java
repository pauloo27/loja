package me.pauloo27.java.view.scenes;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import me.pauloo27.java.db.models.Product;
import me.pauloo27.java.services.ProductService;
import me.pauloo27.java.utils.AppException;
import me.pauloo27.java.view.WinBase;
import me.pauloo27.java.view.scenes.listeners.ProductCreationListener;

public class WinListProduct extends WinBase implements ProductCreationListener {
    private ProductService productService;
    private JTable table;

    public WinListProduct() {
        super("Listar Produtos");
        this.productService = new ProductService();
        this.postSetupComponents();
    }

    private void postSetupComponents() {
        String[] columns = { "Nome", "Quantidade", "Preço" };
        table = new JTable(new DefaultTableModel(new Object[][] {}, columns));
        var scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.addAt(scroll, 10, 50, 480, 180);

        JButton btnNewProduct = new JButton("Novo Produto");
        this.addAt(btnNewProduct, 10, 10, 200, 30);

        btnNewProduct.addActionListener((a) -> {
            WinNewProduct newProductWin = new WinNewProduct();
            newProductWin.addProductCreationListener(this);
            newProductWin.setVisible(true);
        });

        refreshTableData();
    }

    private void refreshTableData() {
        try {
            Collection<Product> products = productService.findAll();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            for (Product product : products) {
                model.addRow(new Object[] { product.getName(), product.getAmount(), product.getPrice() });
            }
        } catch (AppException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void onProductCreated(Product product) {
        this.refreshTableData();
    }
}

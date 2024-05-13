package me.pauloo27.java.view.scenes;

import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.AbstractAction;
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
import me.pauloo27.java.view.utils.TableButton;

public class WinListProduct extends WinBase implements ProductCreationListener {
    private ProductService productService;
    private JTable table;

    public WinListProduct() {
        super("Listar Produtos");
        this.productService = new ProductService();
        this.postSetupComponents();
    }

    private void postSetupComponents() {
        String[] columns = { "ID", "Nome", "Quantidade", "Preço", "Apagar" };
        table = new JTable(new DefaultTableModel(new Object[][] {}, columns));
        var scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.addAt(scroll, 10, 50, 480, 180);

        var action = new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                handleDelete(e);
            }
        };

        new TableButton(table, action, 4);

        JButton btnNewProduct = new JButton("Novo Produto");
        this.addAt(btnNewProduct, 10, 10, 200, 30);

        btnNewProduct.addActionListener((a) -> {
            WinNewProduct newProductWin = new WinNewProduct();
            newProductWin.addProductCreationListener(this);
            newProductWin.setVisible(true);
        });

        refreshTableData();
    }

    private void handleDelete(ActionEvent e) {
        JTable table = (JTable) e.getSource();
        int modelRow = Integer.valueOf(e.getActionCommand());
        Object id = table.getModel().getValueAt(modelRow, 0);

        var confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente apagar este produto?", "Confirmação",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            this.productService.deleteByID((int) id);
            JOptionPane.showMessageDialog(this, "Produto apagado com sucesso", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            this.refreshTableData();
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), ex.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTableData() {
        try {
            Collection<Product> products = productService.findAll();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            for (Product product : products) {
                model.addRow(new Object[] { product.getId(), product.getName(), product.getAmount(), product.getPrice(),
                        "Apagar" });
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

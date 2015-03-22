package stat.graphics;

import stat.controllers.ProductController;
import stat.exception.SoldProductDeletionException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * Created by Burcu Basak SARIKAYA on 3/18/2015. S000855 burcu.sarikaya@ozu.edu.tr
 */

@org.springframework.stereotype.Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ProductMainPage extends Page {

    @Autowired
    private ProductController productController;

    @Autowired
    private ProductAddPage pageNewProduct;

    private JTable            productTable;
    private ProductTableModel tableModel;
    private ProductPageButtonListener buttonListener;

    private JButton removeProductButton;
    private JButton viewProductButton;
    private JButton addProductButton;

    public ProductMainPage() {
        buttonListener = new ProductPageButtonListener();
        initPage();
        initProductTable();
        initButtons();
    }

    private void initPage() {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout(0, 0));
    }

    private void initProductTable() {
        tableModel = new ProductTableModel(new Object[0][0], ProductColType.getColNameList());
        productTable = new JTable(tableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(productTable), BorderLayout.CENTER);
    }

    private void initButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 3, 0, 0));
        createRemoveButton(buttonPanel);
        createViewDetailButton(buttonPanel);
        createAddButton(buttonPanel);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createRemoveButton(JPanel buttonPanel) {
        removeProductButton = new JButton("Delete Product");
        removeProductButton.addActionListener(buttonListener);
        buttonPanel.add(removeProductButton);
    }

    private void createViewDetailButton(JPanel buttonPanel) {
        viewProductButton = new JButton("View Product");
        viewProductButton.addActionListener(buttonListener);
        buttonPanel.add(viewProductButton);
    }

    private void createAddButton(JPanel buttonPanel) {
        addProductButton = new JButton("Add Product");
        addProductButton.addActionListener(buttonListener);
        buttonPanel.add(addProductButton);
    }

    public void setProductsList(Object[][] items) {
        tableModel.setDataVector(items, ProductColType.getColNameList());
        // Refresh table view
        this.repaint();
        this.validate();
    }

    public void clearProductsList() {
        tableModel.setRowCount(0);
    }

    public void showProductDeletionError(SoldProductDeletionException exception) {
        //TODO show pop-up saying Unable to delete product. Sales made to following customers already.
    }

    private class ProductPageButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = productTable.getSelectedRow();
            Object sourceOfAction = e.getSource();
            if (sourceOfAction.equals(addProductButton)) {
                showAddProductWindow();
            } else if (sourceOfAction.equals(removeProductButton)) {
                removeProduct(row);
            } else if (sourceOfAction.equals(viewProductButton)) {
                showProductDetailsWindow(row);
            }
        }
    }

    private void showAddProductWindow() {
        showPopup(pageNewProduct);
    }

    private void removeProduct(int row) {
        if (row != -1) {
            //TODO: Confirm option must be added.
            productController.removeProduct(row);
        }
    }

    private void showProductDetailsWindow(int row) {
        if (row != -1) {
            productController.showProductDetails(row);
        }
    }

    public void displayProductDetailWindow(String name, String description, String price) {
        ProductViewPage view = new ProductViewPage(name, description, price);
        showPopup(view);
    }
}

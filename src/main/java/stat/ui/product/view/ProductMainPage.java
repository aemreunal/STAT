package stat.ui.product.view;

import stat.service.exception.SoldProductDeletionException;
import stat.ui.Page;
import stat.ui.product.ProductController;

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

    private JTable                    productTable;
    private ProductTableModel         tableModel;
    private ProductPageButtonListener buttonListener;

    private JButton            removeProductButton;
    private JButton            viewProductButton;
    private JButton            addProductButton;
    private ProductTableSorter productTableSorter;

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
        tableModel = new ProductTableModel();
        productTableSorter = new ProductTableSorter(tableModel);
        productTable = new JTable(tableModel);
        productTable.setRowSorter(productTableSorter);
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
    }

    private class ProductPageButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = getRow();
            Object sourceOfAction = e.getSource();
            if (sourceOfAction.equals(addProductButton)) {
                showAddProductWindow();
            } else if (sourceOfAction.equals(removeProductButton)) {
                removeProduct(selectedRow);
            } else if (sourceOfAction.equals(viewProductButton)) {
                showProductDetailsWindow(selectedRow);
            }
        }

        private int getRow() {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) { // Check whether any row is selected
                // Convert from [possibly] sorted view row index to underlying model row index
                selectedRow = productTableSorter.convertRowIndexToModel(selectedRow);
            }
            return selectedRow;
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

    public void displayProductDeletionError(SoldProductDeletionException exception) {
        JOptionPane.showMessageDialog(this, exception.getErrorMessage(), "Delete Error", JOptionPane.ERROR_MESSAGE);
    }
}

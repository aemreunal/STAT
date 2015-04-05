package stat.ui.product.main.view;

/*
 * ******************************* *
 * Copyright (c) 2015              *
 *                                 *
 * Sales Tracking & Analytics Tool *
 *                                 *
 * @author Ahmet Emre Ünal         *
 * @author Uğur Özkan              *
 * @author Burcu Başak Sarıkaya    *
 * @author Eray Tuncer             *
 *                                 *
 * ******************************* *
 */

import stat.service.exception.SoldProductDeletionException;
import stat.ui.Page;
import stat.ui.product.main.ProductController;
import stat.ui.product.main.view.helper.ProductColType;
import stat.ui.product.main.view.helper.ProductTableModel;
import stat.ui.product.main.view.helper.ProductTableSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ProductMainPage extends Page {

    @Autowired
    private ProductController productController;

    private JTable                    productTable;
    private ProductTableModel         tableModel;
    private ProductPageButtonListener buttonListener;

    private JButton            addProductButton;
    private JButton            removeProductButton;
    private JButton            viewProductButton;
    private ProductFilterPanel filterPanel;

    public ProductMainPage() {
        buttonListener = new ProductPageButtonListener();
        initPage();
        initProductTable();
        initFilters();
        initButtons();
    }

    protected void initPage() {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout(0, 0));
    }

    private void initProductTable() {
        tableModel = new ProductTableModel();
        tableSorter = new ProductTableSorter(tableModel);
        productTable = new JTable(tableModel);
        productTable.setRowSorter(tableSorter);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(productTable), BorderLayout.CENTER);
    }

    private void initFilters() {
        filterPanel = new ProductFilterPanel(this);
        add(filterPanel, BorderLayout.NORTH);
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
                productController.addProductButtonClicked();
            } else if (sourceOfAction.equals(removeProductButton)) {
                productController.removeProductButtonClicked(selectedRow);
            } else if (sourceOfAction.equals(viewProductButton)) {
                productController.viewProductButtonClicked(selectedRow);
            }
        }

        private int getRow() {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) { // Check whether any row is selected
                // Convert from [possibly] sorted view row index to underlying model row index
                selectedRow = tableSorter.convertRowIndexToModel(selectedRow);
            }
            return selectedRow;
        }
    }

    public void applyFilterButtonClicked() {
        productController.applyFilterButtonClicked(filterPanel);
    }

    public void displayProductDeletionError(SoldProductDeletionException exception) {
        JOptionPane.showMessageDialog(this, exception.getErrorMessage(), "Delete Error", JOptionPane.ERROR_MESSAGE);
    }
}

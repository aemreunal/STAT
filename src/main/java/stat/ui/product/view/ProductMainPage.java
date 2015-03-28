package stat.ui.product.view;

import stat.service.exception.SoldProductDeletionException;
import stat.ui.Page;
import stat.ui.product.ProductController;
import stat.ui.product.view.helper.ProductColType;
import stat.ui.product.view.helper.ProductTableModel;
import stat.ui.product.view.helper.ProductTableSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * Created by Burcu Basak SARIKAYA on 3/18/2015. S000855 burcu.sarikaya@ozu.edu.tr
 */

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ProductMainPage extends Page {

    @Autowired
    private ProductController productController;

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
        initFilters();
        initProductTable();
        initButtons();
    }

    private void initPage() {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout(0, 0));
    }

    private void initFilters() {
        JPanel filterHolder = new JPanel();
        filterHolder.setLayout(new GridLayout(1, 3));

        JTextField productnameFilterField = new JTextField();
        productnameFilterField.getDocument().addDocumentListener(new FilterFieldListener(0));
        filterHolder.add(productnameFilterField);

        JTextField descriptionFilterField = new JTextField();
        descriptionFilterField.getDocument().addDocumentListener(new FilterFieldListener(1));
        filterHolder.add(descriptionFilterField);

        JTextField unitpriceFilterField = new JTextField();
        unitpriceFilterField.getDocument().addDocumentListener(new FilterFieldListener(2));
        filterHolder.add(unitpriceFilterField);

        add(filterHolder, BorderLayout.NORTH);
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
                selectedRow = productTableSorter.convertRowIndexToModel(selectedRow);
            }
            return selectedRow;
        }
    }

    public void displayProductDeletionError(SoldProductDeletionException exception) {
        JOptionPane.showMessageDialog(this, exception.getErrorMessage(), "Delete Error", JOptionPane.ERROR_MESSAGE);
    }

    private class FilterFieldListener implements DocumentListener {

        private int columnIndex;

        public FilterFieldListener(int columnIndex) {
            this.columnIndex = columnIndex;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            filterTable(getText(e));
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            filterTable(getText(e));
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            filterTable(getText(e));
        }

        private String getText(DocumentEvent event) {
            try {
                Document document = event.getDocument();
                return document.getText(0, document.getLength());
            } catch (BadLocationException e1) {
                e1.printStackTrace();
                return new String();
            }
        }

        private void filterTable(String filterText) {
            productTableSorter.setRowFilter(RowFilter.regexFilter(filterText, columnIndex));
        }

    }

}

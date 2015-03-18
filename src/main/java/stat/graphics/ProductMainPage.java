package stat.graphics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import stat.controllers.ProductController;
import stat.domain.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Burcu Basak SARIKAYA on 3/18/2015.
 * S000855
 * burcu.sarikaya@ozu.edu.tr
 */

@org.springframework.stereotype.Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ProductMainPage extends Page {

    @Autowired
    private ProductController productController; // TODO: make use of

    @Autowired
    private ProductAddPage pageNewProduct;

    private JTable productTable;
    private ArrayList<Integer> productIDList = new ArrayList<Integer>();

    public ProductMainPage() {
        initPage();
        initProductTable();
        initButtons();
    }

    private void initPage() {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout(0, 0));
    }

    private void initProductTable() {
        productTable = new JTable(getProductTableModel());
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    private TableModel getProductTableModel() {
        // TODO: Remove static data
        Object[][] data = { {"elma", "Amasya Elması", "3.5"},
                {"armut", "Deveci armudu", "5.99"}};
        String[] columnNames = {"Product Name", "Description", "Unit Price"};
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return tableModel;
    }

    private void initButtons() {
        JPanel buttonHolder = new JPanel();
        buttonHolder.setLayout(new GridLayout(0, 3, 0, 0));
        buttonHolder.add(createButtonRemove());
        buttonHolder.add(createButtonView());
        buttonHolder.add(createButtonAdd());

        add(buttonHolder, BorderLayout.SOUTH);
    }

    private JButton createButtonRemove() {
        JButton buttonRemoveProduct = new JButton("Delete Product");
        buttonRemoveProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: implement
            }
        });
        return buttonRemoveProduct;
    }

    private JButton createButtonView() {
        JButton buttonRemoveProduct = new JButton("View Product");
        buttonRemoveProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: implement
            }
        });
        return buttonRemoveProduct;
    }

    private JButton createButtonAdd() {
        JButton buttonAddProduct = new JButton("Add Product");
        buttonAddProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPopup(pageNewProduct);
            }
        });
        return buttonAddProduct;
    }

    private void showPopup(Page page) {
        JFrame popupWindow = new JFrame();
        popupWindow.setContentPane(page);
        popupWindow.setSize(page.getSize());
        popupWindow.setResizable(false);
        popupWindow.setVisible(true);
    }

    public void emptyProducts() {
        DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
        int numberRows = tableModel.getRowCount();
        for (int rowIndex = 0; rowIndex < numberRows; rowIndex++) {
            tableModel.removeRow(rowIndex);
        }
        productIDList = new ArrayList<Integer>();
    }

    public void removeRow(int rowIndex) {
        int productID = productIDList.get(rowIndex);
        DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
        tableModel.removeRow(rowIndex);
        productIDList.remove(rowIndex);
        productController.removeProduct(productID);
    }

    public void addProducts(Set<Product> productSet) {
        for (Product product : productSet) {
            addProduct(product);
        }
    }

    public void addProduct(Product product) {
        DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
        Object[] productRow = new Object[3];
        productRow[0] = product.getName();
        productRow[1] = product.getDescription();
        productRow[2] = product.getPrice();

        tableModel.addRow(productRow);
        productIDList.add(product.getProductId());
    }

}

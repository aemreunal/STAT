package stat.ui.sale.main.view;

import stat.domain.Product;
import stat.domain.Sale;
import stat.ui.Page;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * Created by Eray Tuncer S000926 eray.tuncer@ozu.edu.tr
 */
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")

public class SaleViewPage extends Page {

    private final Sale sale;
    private final LinkedHashMap<Product, Integer> productsAndAmounts;
    private JTextField customerNameField;
    private JTextField totalPriceField;
    private JTextField dateField;
    private JTable     productTable;

    public SaleViewPage(Sale sale, LinkedHashMap<Product, Integer> productsAndAmounts) {
        this.sale = sale;
        this.productsAndAmounts = productsAndAmounts;
        initPage();
        initCustomerNameField();
        initTotalPriceField();
        initDateField();
        initProductTable();
    }

    private void initPage() {
        setLayout(null);
        setSize(new Dimension(500, 500));
    }

    private void initCustomerNameField() {
        JLabel customerNameLabel = new JLabel("Customer Name :");
        customerNameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        customerNameLabel.setBounds(30, 25, 131, 17);

        customerNameField = new JTextField(sale.getCustomerName());
        customerNameField.setBounds(172, 25, 215, 20);
        customerNameField.setColumns(10);
        customerNameField.setEditable(false);

        add(customerNameLabel);
        add(customerNameField);
    }

    private void initTotalPriceField() {
        JLabel totalPriceLabel = new JLabel("Total Price :");
        totalPriceLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        totalPriceLabel.setBounds(71, 53, 90, 17);

        totalPriceField = new JTextField(String.valueOf(sale.getTotalPrice()));
        totalPriceField.setColumns(10);
        totalPriceField.setBounds(172, 53, 215, 20);
        totalPriceField.setEditable(false);

        add(totalPriceLabel);
        add(totalPriceField);
    }

    private void initDateField() {
        JLabel dateLabel = new JLabel("Date :");
        dateLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        dateLabel.setBounds(111, 81, 50, 17);

        dateField = new JTextField(String.valueOf(sale.getDate()));
        dateField.setColumns(10);
        dateField.setBounds(172, 81, 215, 20);
        dateField.setEditable(false);

        add(dateLabel);
        add(dateField);
    }

    private void initProductTable() {
        productTable = new JTable(createTableModel());

        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setBounds(30, 109, 437, 337);
        scrollPanel.setViewportView(productTable);
        add(scrollPanel);

        for (Map.Entry<Product, Integer> entry : productsAndAmounts.entrySet()) {
            Product product = entry.getKey();
            Integer amount = entry.getValue();
            BigDecimal price = product.getPrice().multiply(BigDecimal.valueOf(amount));
            addProductDetailsToTable(product.getName(), amount, price);
        }
    }

    private TableModel createTableModel() {
        Object[][] data = { };
        String[] columnNames = { "Product Name", "Amount", "Price" };
        return new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public void addProductDetailsToTable(String productName, int amount, BigDecimal price) {
        //TODO implement. Auto-generated for SaleController.java
    }

    public void setCustomerNameField(String customerName) {
        //TODO implement. Auto-generated for SaleController.java
    }

    public void setDateField(Date date) {
        //TODO implement. Auto-generated for SaleController.java
    }

    public void setTotalPriceField(BigDecimal sum) {
        //TODO implement. Auto-generated for SaleController.java
    }
}

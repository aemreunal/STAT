package stat.graphics;

import stat.domain.Sale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * Created by Eray Tuncer
 * S000926
 * eray.tuncer@ozu.edu.tr
 */
public class SaleViewPage extends Page {

    private JTextField customerNameField;
    private JTextField totalPriceField;
    private JTextField dateField;
    private JTable     productTable;

    public SaleViewPage() {
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

        customerNameField = new JTextField();
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

        totalPriceField = new JTextField();
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

        dateField = new JTextField();
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
    }

    private TableModel createTableModel() {
        Object[][] data = {};
        String[] columnNames = {"Product Name", "Amount", "Price"};
        return new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public void setSale(Sale sale) {
        // TODO: implement
        if (sale != null) {

        }
    }

}

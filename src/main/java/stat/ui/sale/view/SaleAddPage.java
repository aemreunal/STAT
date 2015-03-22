package stat.ui.sale.view;

import stat.ui.Page;
import stat.ui.sale.SaleController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * Created by Burcu Basak SARIKAYA S000855 burcu.sarikaya@ozu.edu.tr
 */

public class SaleAddPage extends Page {

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    private SaleController saleController;

    private JTextField textfieldCustomerName;
    private JTable     productTable, saleProductTable;
    private JTextField textfieldTotalPrice;
    private JTextField dateField;
    private JButton    backButton, confirmButton;
    private ButtonListener buttonListener;
    private JButton        buttonAdd, buttonRemove;
    private JScrollPane productListPane, saleProductListPane;
    public static final String DATE_REGEX = "^([0-9]{4})-([0]?[1-9]|[1][0-2])-([0]?[1-9]|[1|2][0-9]|[3][0|1])$";

    public SaleAddPage(SaleController saleController) {
        this.saleController = saleController;
        buttonListener = new ButtonListener();
        initPageDesign();
        initBackButton();
        initCustomerNameField();
        initDateField();
        initProductTable();
        initAddButton();
        initRemoveButton();
        initSaleProductTable();
        initTotalPrice();
        initConfirmButton();
    }

    private void initPageDesign() {
        setLayout(null);
        setBackground(new Color(204, 204, 0));
        setMinimumSize(new Dimension(500, 450));
    }

    private void initBackButton() {
        backButton = new JButton("BACK");
        backButton.setForeground(new Color(204, 51, 51));
        backButton.setBackground(new Color(245, 245, 245));
        backButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        backButton.setBounds(29, 28, 437, 30);
        backButton.addActionListener(buttonListener);
        add(backButton);
    }

    private void initCustomerNameField() {
        JLabel labelCustomerName = new JLabel("Customer Name : ");
        labelCustomerName.setHorizontalAlignment(SwingConstants.RIGHT);
        labelCustomerName.setFont(new Font("Tahoma", Font.BOLD, 13));
        labelCustomerName.setBounds(29, 74, 125, 24);

        textfieldCustomerName = new JTextField();
        textfieldCustomerName.setBounds(164, 77, 201, 20);
        textfieldCustomerName.setColumns(10);

        add(labelCustomerName);
        add(textfieldCustomerName);
    }

    private void initDateField() {
        JLabel labelDate = new JLabel("Date : ");
        labelDate.setHorizontalAlignment(SwingConstants.RIGHT);
        labelDate.setFont(new Font("Tahoma", Font.BOLD, 13));
        labelDate.setBounds(29, 109, 125, 24);

        dateField = new JTextField("yyyy-MM-dd");
        dateField.setBounds(164, 111, 201, 22);

        add(labelDate);
        add(dateField);
    }

    private void initProductTable() {
        productTable = new JTable(getProductTableModel());
        productListPane = new JScrollPane(productTable);
        productListPane.setBounds(10, 155, 125, 145);
        add(productListPane);
    }

    private TableModel getProductTableModel() {
        String[] columnNames = { "Product" };
        return new DefaultTableModel(new Object[][] { }, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public void fillProducts(Set<String> productNames) {
        // Clear product list
        ((DefaultTableModel) productTable.getModel()).setRowCount(0);

        // Populate with product names
        for (String productName : productNames) {
            ((DefaultTableModel) productTable.getModel()).addRow(new Object[] { productName });
        }
    }

    private void initSaleProductTable() {
        saleProductTable = new JTable(getSaleTableModel());
        saleProductListPane = new JScrollPane(saleProductTable);
        saleProductListPane.setBounds(224, 155, 266, 145);
        add(saleProductListPane);
    }

    private TableModel getSaleTableModel() {
        String[] columnNames = { "Product", "Amount", "Price" };
        return new DefaultTableModel(new Object[][] { }, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return (column == 1);
            }

            public void setValueAt(Object aValue, int row, int column) {
                if (column == 1) {
                    String regex = "[0-9]+";
                    String value = (String) aValue;
                    if (value.matches(regex)) {
                        super.setValueAt(aValue, row, column);
                        String productName = (String) saleProductTable.getValueAt(row, 0);
                        BigDecimal price = saleController.calculatePrice(productName, Integer.parseInt((value)));
                        saleProductTable.setValueAt("" + price, row, 2);
                        updateTotalPrice();
                    }
                } else {
                    super.setValueAt(aValue, row, column);
                }
            }
        };
    }

    private void initAddButton() {
        buttonAdd = new JButton("ADD");
        buttonAdd.setFont(new Font("Tahoma", Font.BOLD, 11));
        buttonAdd.setForeground(new Color(106, 90, 205));
        buttonAdd.setBounds(145, 190, 69, 23);
        buttonAdd.addActionListener(buttonListener);
        add(buttonAdd);
    }

    private void initRemoveButton() {
        buttonRemove = new JButton("DEL");
        buttonRemove.setForeground(new Color(165, 42, 42));
        buttonRemove.setFont(new Font("Tahoma", Font.BOLD, 11));
        buttonRemove.setBounds(145, 224, 69, 23);
        buttonRemove.addActionListener(buttonListener);
        add(buttonRemove);
    }

    private void initTotalPrice() {
        JLabel labelTotal = new JLabel("Total :");
        labelTotal.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelTotal.setBounds(292, 311, 49, 24);

        textfieldTotalPrice = new JTextField();
        textfieldTotalPrice.setBounds(341, 315, 125, 20);
        textfieldTotalPrice.setColumns(10);
        textfieldTotalPrice.setEditable(false);

        add(labelTotal);
        add(textfieldTotalPrice);
    }

    private void initConfirmButton() {
        confirmButton = new JButton("CONFIRM");
        confirmButton.setForeground(new Color(0, 153, 51));
        confirmButton.setBackground(new Color(245, 245, 245));
        confirmButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        confirmButton.setBounds(29, 362, 437, 30);
        confirmButton.addActionListener(buttonListener);
        add(confirmButton);
    }

    private void updateTotalPrice() {
        double totalPrice = 0;
        for (int row = 0; row < saleProductTable.getRowCount(); row++) {
            totalPrice += Double.parseDouble((String) saleProductTable.getValueAt(row, 2));
        }
        textfieldTotalPrice.setText("" + totalPrice);
    }

    private ArrayList<String> getProducts() {
        ArrayList<String> productNames = new ArrayList<>();
        for (int row = 0; row < saleProductTable.getRowCount(); row++) {
            String productName = (String) saleProductTable.getValueAt(row, 0);
            productNames.add(productName);
        }
        return productNames;
    }

    private ArrayList<Integer> getAmounts() {
        ArrayList<Integer> productAmounts = new ArrayList<>();
        for (int row = 0; row < saleProductTable.getRowCount(); row++) {
            int productAmount = Integer.parseInt(saleProductTable.getValueAt(row, 1).toString());
            productAmounts.add(productAmount);
        }
        return productAmounts;
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object sourceOfAction = e.getSource();
            if (sourceOfAction.equals(backButton)) {
                JFrame parentFrame = (JFrame) getRootPane().getParent();
                parentFrame.dispose();
            } else if (sourceOfAction.equals(buttonAdd)) {
                addProductToSale();
            } else if (sourceOfAction.equals(buttonRemove)) {
                removeProductFromSale();
            } else if (sourceOfAction.equals(confirmButton)) {
                saveSale();
            }
        }
    }

    private void addProductToSale() {
        int row = productTable.getSelectedRow();
        if (row != -1) {
            String productName = (String) productTable.getValueAt(row, 0);
            ((DefaultTableModel) productTable.getModel()).removeRow(row);
            BigDecimal unitPrice = saleController.calculatePrice(productName, 1);
            ((DefaultTableModel) saleProductTable.getModel()).addRow(new Object[] { productName, 1, "" + unitPrice });
        }
        updateTotalPrice();
    }

    private void removeProductFromSale() {
        int row = saleProductTable.getSelectedRow();
        if (row != -1) {
            String productName = (String) saleProductTable.getValueAt(row, 0);
            ((DefaultTableModel) saleProductTable.getModel()).removeRow(row);
            ((DefaultTableModel) productTable.getModel()).addRow(new Object[] { productName });
        }
        updateTotalPrice();
    }

    private void saveSale() {
        String dateText = dateField.getText().trim();
        if (!dateText.matches(DATE_REGEX)) {
            showDateParseError();
            return;
        }
        boolean saleSaved = saleController.saveSale(textfieldCustomerName.getText().trim(), parseDate(dateText), getProducts(), getAmounts());
        if (saleSaved) {
            displaySuccess();
        } else {
            displayValidationError();
        }
    }

    private Date parseDate(String dateText) {
        try {
            return dateFormatter.parse(dateText);
        } catch (ParseException pe) {
            showDateParseError();
        }
        return null;
    }

    private void showDateParseError() {
        JOptionPane.showMessageDialog(this,
                                      "Please enter a valid date.",
                                      "Validation Error",
                                      JOptionPane.ERROR_MESSAGE);
    }

    public void displaySuccess() {
        JOptionPane.showMessageDialog(this, "The Sale was successfully saved.");
    }

    public void displayValidationError() {
        JOptionPane.showMessageDialog(this,
                                      "Enter the fields correctly.",
                                      "Validation Error",
                                      JOptionPane.ERROR_MESSAGE);
    }
}

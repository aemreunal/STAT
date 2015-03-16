package stat.graphics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import stat.controllers.SaleController;

/**
 * Created by Burcu Basak SARIKAYA S000855 burcu.sarikaya@ozu.edu.tr
 */

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class NewSalePage extends Page {

    @Autowired
    private SaleController saleController;

    @Autowired
    private Page menuPage;

    private JTextField     textfieldCustomerName;
    private JTable         productTable, saleProductTable;
    private JTextField     textfieldTotalPrice;
    private JTextField     dateField;
    private JButton        backButton, confirmButton;
    private ButtonListener buttonListener;
    private JButton		   buttonAdd, buttonRemove;
    private JScrollPane    productListPane, saleProductListPane;

    public NewSalePage() {
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
        setSize(500, 450);
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

        dateField = new JTextField("dd.mm.yyyy");
        dateField.setBounds(164, 111, 201, 22);

        add(labelDate);
        add(dateField);
    }

    private void initProductTable() {
        productTable = new JTable(getProductTableModel());
        productListPane = new JScrollPane(productTable);
        productListPane.setBounds(10, 155, 125, 145);
        fillProducts();
        add(productListPane);
    }

    private TableModel getProductTableModel() {
        String[] columnNames = {"Product"};
        return new DefaultTableModel(new Object[][]{}, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void fillProducts() {

    }

    private void initSaleProductTable() {
        saleProductTable = new JTable(getSaleTableModel());
        saleProductListPane = new JScrollPane(saleProductTable);
        saleProductListPane.setBounds(224, 155, 266, 145);
        add(saleProductListPane);
    }

    private TableModel getSaleTableModel() {
        String[] columnNames = {"Product", "Amount", "Price"};
        return new DefaultTableModel(new Object[][]{}, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return (column == 1);
            }

            public void setValueAt(Object aValue, int row, int column) {
                if (isCellEditable(row, column)) {
                    String regex = "[0-9]+";
                    String value = (String) aValue;
                    if (value.matches(regex)) {
                        super.setValueAt(aValue, row, column);
                        updateTotalPrice();
                    }
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
        for(int row = 0; row < saleProductTable.getRowCount(); row++) {
            totalPrice += (double) saleProductTable.getValueAt(row, 2);
        }
        textfieldTotalPrice.setText("" + totalPrice);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ApplicationWindow appWindow = getApplicationWindow();
            Object sourceOfAction = e.getSource();
            if (sourceOfAction instanceof JButton) {
                if (sourceOfAction.equals(backButton)) {
                    // TODO: change the functionality
                    //appWindow.setCurrentPage(menuPage);
                } else if (sourceOfAction.equals(buttonAdd)) {
                    addProductToSale();
                } else if (sourceOfAction.equals(buttonRemove)) {
                    removeProductFromSale();
                } else if (sourceOfAction.equals(confirmButton)) {
                    // TODO: implement
                }
            }
        }

        private void addProductToSale() {
            int row = productTable.getSelectedRow();
            if(row != -1) {
                String productName = (String) productTable.getValueAt(row, 0);
                ((DefaultTableModel) productTable.getModel()).removeRow(row);
                double unitPrice = saleController.calculatePrice(productName, 1);
                ((DefaultTableModel) saleProductTable.getModel()).addRow(new Object[]{productName, 1, unitPrice});
            }
            updateTotalPrice();
        }

        private void removeProductFromSale() {
            int row = saleProductTable.getSelectedRow();
            if(row != -1) {
                String productName = (String) saleProductTable.getValueAt(row, 0);
                ((DefaultTableModel) saleProductTable.getModel()).removeRow(row);
                ((DefaultTableModel) productTable.getModel()).addRow(new Object[]{productName});
            }
            updateTotalPrice();
        }

    }
}
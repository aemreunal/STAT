package stat.ui.sale.add.view;

import stat.ui.Page;
import stat.ui.sale.add.SaleAddController;
import stat.ui.sale.add.view.helper.AvailableProductsTableModel;
import stat.ui.sale.add.view.helper.ChosenProductsTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * Created by Burcu Basak SARIKAYA S000855 burcu.sarikaya@ozu.edu.tr
 */

public class SaleAddPage extends Page {

    private SaleAddController saleAddController;

    private JTextField customerNameField;
    private JTextField priceField;
    private JTextField dateField;

    private JPanel fieldHolder;

    private JTable availableProductsTable;
    private JTable chosenProductsTable;

    private JButton backButton;
    private JButton confirmButton;
    private JButton addProductButton;
    private JButton removeProductButton;

    private ButtonListener              buttonListener;
    private AvailableProductsTableModel availableProductsTableModel;
    private ChosenProductsTableModel    chosenProductsTableModel;

    public SaleAddPage(SaleAddController saleAddController) {
        this.saleAddController = saleAddController;
        buttonListener = new ButtonListener();
        initPageDesign();
        initFielHolder();
        initFields();
        initTables();
        initButtons();
    }

    private void initPageDesign() {
        setLayout(null);
        setMinimumSize(new Dimension(500, 450));
    }

    private void initFielHolder() {
        fieldHolder = new JPanel();
        fieldHolder.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Sale Info", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        fieldHolder.setBounds(2, 22, 492, 277);
        fieldHolder.setLayout(null);
        add(fieldHolder);
    }

    private void initFields() {
        initCustomerNameField();
        initDateField();
        initTotalPrice();
    }

    private void initTables() {
        initAvailableProductsTable();
        initChosenProductsTable();
    }

    private void initButtons() {
        initCancelButton();
        initAddButton();
        initRemoveButton();
        initConfirmButton();
    }

    private void initAddButton() {
        addProductButton = new JButton("Add");
        addProductButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        addProductButton.setBounds(133, 124, 85, 23);
        addProductButton.addActionListener(buttonListener);
        fieldHolder.add(addProductButton);
    }

    private void initRemoveButton() {
        removeProductButton = new JButton("Remove");
        removeProductButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        removeProductButton.setBounds(133, 158, 85, 23);
        removeProductButton.addActionListener(buttonListener);
        fieldHolder.add(removeProductButton);
    }

    private void initConfirmButton() {
        confirmButton = new JButton("Create");
        confirmButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        confirmButton.setBounds(29, 318, 437, 30);
        confirmButton.addActionListener(buttonListener);
        add(confirmButton);
    }

    private void initCancelButton() {
        backButton = new JButton("Cancel");
        backButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        backButton.setBounds(29, 362, 437, 30);
        backButton.addActionListener(buttonListener);
        add(backButton);
    }

    private void initCustomerNameField() {
        JLabel labelCustomerName = new JLabel("Customer Name:");
        labelCustomerName.setHorizontalAlignment(SwingConstants.RIGHT);
        labelCustomerName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        labelCustomerName.setBounds(25, 12, 125, 24);

        customerNameField = new JTextField();
        customerNameField.setBounds(160, 15, 201, 20);
        customerNameField.setColumns(10);

        fieldHolder.add(labelCustomerName);
        fieldHolder.add(customerNameField);
    }

    private void initAvailableProductsTable() {
        availableProductsTableModel = new AvailableProductsTableModel();
        availableProductsTable = new JTable(availableProductsTableModel);
        JScrollPane availableProductsListPane = new JScrollPane(availableProductsTable);
        availableProductsListPane.setBounds(6, 79, 125, 145);
        fieldHolder.add(availableProductsListPane);
    }

    private void initChosenProductsTable() {
        chosenProductsTableModel = new ChosenProductsTableModel(this);
        chosenProductsTable = new JTable(chosenProductsTableModel);
        JScrollPane chosenProductsListPane = new JScrollPane(chosenProductsTable);
        chosenProductsListPane.setBounds(220, 79, 266, 145);
        fieldHolder.add(chosenProductsListPane);
    }

    private void initDateField() {
        JLabel labelDate = new JLabel("Date:");
        labelDate.setHorizontalAlignment(SwingConstants.RIGHT);
        labelDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
        labelDate.setBounds(25, 43, 125, 24);

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        dateField = new JTextField(year + "-" + month + "-" + day);
        dateField.setBounds(160, 45, 201, 22);

        fieldHolder.add(labelDate);
        fieldHolder.add(dateField);
    }

    private void initTotalPrice() {
        JLabel labelTotal = new JLabel("Total:");
        labelTotal.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelTotal.setBounds(288, 245, 49, 24);

        priceField = new JTextField(String.valueOf(BigDecimal.ZERO));
        priceField.setBounds(337, 249, 125, 20);
        priceField.setColumns(10);
        priceField.setEditable(false);

        fieldHolder.add(labelTotal);
        fieldHolder.add(priceField);
    }

    public void setAvailableProducts(Object[][] availableProductsNames) {
        availableProductsTableModel.setDataVector(availableProductsNames, AvailableProductsTableModel.AVAILABLE_PRODUCTS_TABLE_COLUMN_NAMES);
    }

    public void setChosenProducts(Object[][] chosenProductsNames) {
        chosenProductsTableModel.setDataVector(chosenProductsNames, ChosenProductsTableModel.CHOSEN_PRODUCTS_TABLE_COLUMN_NAMES);
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object sourceOfAction = e.getSource();
            if (sourceOfAction.equals(backButton)) {
                closeAddPage();
            } else if (sourceOfAction.equals(addProductButton)) {
                int availableProdsRow = availableProductsTable.getSelectedRow(); // TODO fix for sort
                if (availableProdsRow != -1) {
                    saleAddController.addProductButtonClicked(availableProdsRow);
                }
            } else if (sourceOfAction.equals(removeProductButton)) {
                int chosenProdsRow = chosenProductsTable.getSelectedRow(); // TODO fix for sort
                if (chosenProdsRow != -1) {
                    saleAddController.removeProductButtonClicked(chosenProdsRow);
                }
            } else if (sourceOfAction.equals(confirmButton)) {
                saleAddController.confirmButtonClicked();
            }
        }
    }

    public void closeAddPage() {
        JFrame parentFrame = (JFrame) getRootPane().getParent();
        parentFrame.dispose();
    }

    public void productAmountChanged(int row, int newAmount) {
        saleAddController.productAmountChanged(row, newAmount);
    }

    public void setChosenProductPrice(int row, BigDecimal newPrice) {
        chosenProductsTable.setValueAt(newPrice, row, 2);
    }

    public void setTotalPrice(String newTotal) {
        this.priceField.setText(newTotal);
    }

    public String getDateText() {
        return this.dateField.getText().trim();
    }

    public String getCustomerNameText() {
        return customerNameField.getText().trim();
    }

    public void showDateParseError() {
        JOptionPane.showMessageDialog(this,
                                      "Please enter a valid date.",
                                      "Validation Error",
                                      JOptionPane.ERROR_MESSAGE);
    }

    public void showCustomerNameParseError() {
        JOptionPane.showMessageDialog(this,
                                      "Please enter a valid customer name.",
                                      "Validation Error",
                                      JOptionPane.ERROR_MESSAGE);
    }

    public void displaySuccess() {
        JOptionPane.showMessageDialog(this, "The Sale was successfully saved.");
    }

    public void showSaveError() {
        JOptionPane.showMessageDialog(this,
                                      "An error occurred while saving the sale. Please try again later.",
                                      "Save Error",
                                      JOptionPane.ERROR_MESSAGE);
    }
}

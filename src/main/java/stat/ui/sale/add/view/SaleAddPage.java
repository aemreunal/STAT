package stat.ui.sale.add.view;

/*
 * ******************************* *
 * Copyright (c) 2015              *
 *                                 *
 * Sales Tracking & Analytics Tool *
 *                                 *
 * @author Ahmet Emre Ünal         *
 * @author Uğur Özkan              *
 * @author Burcu Başak Sarıkaya    *
 * @author Eray Tunçer             *
 *                                 *
 * ******************************* *
 */

import stat.ui.Page;
import stat.ui.sale.add.SaleAddController;
import stat.ui.sale.add.view.helper.AvailableProductsTableModel;
import stat.ui.sale.add.view.helper.ChosenProductsTableModel;
import stat.ui.sale.add.view.helper.CustNameCompletionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class SaleAddPage extends Page {

    private SaleAddController saleAddController;

    private JTextField customerNameField;
    private JTextField priceField;

    private JPanel fieldHolder;

    private JTable availableProductsTable;
    private JTable chosenProductsTable;

    private JButton cancelButton;
    private JButton confirmButton;
    private JButton addProductButton;
    private JButton removeProductButton;

    private ButtonListener              buttonListener;
    private AvailableProductsTableModel availableProductsTableModel;
    private ChosenProductsTableModel    chosenProductsTableModel;
    private UtilDateModel               dateModel;

    public SaleAddPage(SaleAddController saleAddController) {
        this.saleAddController = saleAddController;
        buttonListener = new ButtonListener();
        initPage();
        initFieldHolder();
        initFields();
        initTables();
        initButtons();
    }

    @Override
    protected void initPage() {
        setLayout(null);
        setMinimumSize(new Dimension(500, 450));
    }

    private void initFieldHolder() {
        fieldHolder = new JPanel();
        fieldHolder.setBounds(2, 22, 492, 285);
        fieldHolder.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Sale Info", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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
        addProductButton = new JButton("Add →");
        addProductButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
        addProductButton.setBounds(133, 124, 85, 23);
        addProductButton.addActionListener(buttonListener);
        fieldHolder.add(addProductButton);
    }

    private void initRemoveButton() {
        removeProductButton = new JButton("← Remove");
        removeProductButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
        removeProductButton.setBounds(133, 158, 85, 23);
        removeProductButton.addActionListener(buttonListener);
        fieldHolder.add(removeProductButton);
    }

    private void initConfirmButton() {
        confirmButton = new JButton("Create");
        confirmButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        confirmButton.setBounds(260, 335, 180, 30);
        confirmButton.addActionListener(buttonListener);
        add(confirmButton);
    }

    private void initCancelButton() {
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        cancelButton.setBounds(50, 335, 180, 30);
        cancelButton.addActionListener(buttonListener);
        add(cancelButton);
    }

    private void initCustomerNameField() {
        JLabel labelCustomerName = new JLabel("Customer Name:");
        labelCustomerName.setHorizontalAlignment(SwingConstants.RIGHT);
        labelCustomerName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        labelCustomerName.setBounds(25, 12, 125, 24);

        customerNameField = new JTextField();
        customerNameField.setBounds(160, 15, 201, 20);
        customerNameField.setColumns(10);
        customerNameField.getDocument().addDocumentListener(new CustNameCompletionListener(saleAddController, customerNameField));

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
        labelDate.setBounds(25, 45, 125, 24);

        JDatePickerImpl datePicker = Page.createDatePicker();
        dateModel = (UtilDateModel) datePicker.getModel();
        datePicker.setLocation(160, 45);
        datePicker.setSize(datePicker.getPreferredSize());
        datePicker.addActionListener(e -> {
            if (dateModel.getValue().after(new Date())) {
                JOptionPane.showMessageDialog(null, "You cannot select a date after today!", "Error", JOptionPane.ERROR_MESSAGE);
                dateModel.setValue(new Date());
            }
        });

        fieldHolder.add(labelDate);
        fieldHolder.add(datePicker);
    }

    private void initTotalPrice() {
        JLabel labelTotal = new JLabel("Total:");
        labelTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
            if (sourceOfAction.equals(cancelButton)) {
                closeAddPage();
            } else if (sourceOfAction.equals(addProductButton)) {
                int availableProdsRow = availableProductsTable.getSelectedRow();
                if (availableProdsRow != -1) {
                    saleAddController.addProductButtonClicked(availableProdsRow);
                }
            } else if (sourceOfAction.equals(removeProductButton)) {
                int chosenProdsRow = chosenProductsTable.getSelectedRow();
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

    public Date getDate() {
        return dateModel.getValue();
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

    public void showMissingProductError() {
        JOptionPane.showMessageDialog(this,
                                      "Please add at least one product.",
                                      "Save Error",
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

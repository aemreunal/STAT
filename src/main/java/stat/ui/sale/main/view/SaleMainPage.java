package stat.ui.sale.main.view;

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
import stat.ui.sale.main.SaleController;
import stat.ui.sale.main.view.helper.SaleColType;
import stat.ui.sale.main.view.helper.SaleFilterPanel;
import stat.ui.sale.main.view.helper.SaleTableModel;
import stat.ui.sale.main.view.helper.SaleTableSorter;

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
public class SaleMainPage extends Page {

    @Autowired
    private SaleController saleController;

    private JTable                 saleTable;
    private SaleTableModel         tableModel;
    private SalePageButtonListener buttonListener;

    private JButton         addSaleButton;
    private JButton         removeSaleButton;
    private JButton         viewSaleButton;
    private SaleFilterPanel filterPanel;

    public SaleMainPage() {
        buttonListener = new SalePageButtonListener();
        initPage();
        initSaleTable();
        initFilters();
        initButtons();
    }

    protected void initPage() {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout(0, 0));
    }

    private void initSaleTable() {
        tableModel = new SaleTableModel();
        tableSorter = new SaleTableSorter(tableModel);
        saleTable = new JTable(tableModel);
        saleTable.setRowSorter(tableSorter);
        saleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(saleTable), BorderLayout.CENTER);
    }

    private void initFilters() {
        filterPanel = new SaleFilterPanel(this);
        add(filterPanel, BorderLayout.NORTH);
    }

    private void initButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 50, 0));
        createRemoveButton(buttonPanel);
        createViewButton(buttonPanel);
        createAddButton(buttonPanel);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createRemoveButton(JPanel buttonPanel) {
        removeSaleButton = new JButton("Delete Sale");
        removeSaleButton.addActionListener(buttonListener);
        buttonPanel.add(removeSaleButton);
    }

    private void createViewButton(JPanel buttonPanel) {
        viewSaleButton = new JButton("View Sale");
        viewSaleButton.addActionListener(buttonListener);
        buttonPanel.add(viewSaleButton);
    }

    private void createAddButton(JPanel buttonPanel) {
        addSaleButton = new JButton("Add Sale");
        addSaleButton.addActionListener(buttonListener);
        buttonPanel.add(addSaleButton);
    }

    public void setSalesList(Object[][] items) {
        tableModel.setDataVector(items, SaleColType.getColNameList());
    }

    public int getDeletionConfirmationResult() {
        return JOptionPane.showConfirmDialog(this,"Are you sure?", "Sale Deletion", JOptionPane.YES_NO_OPTION);
    }

    public void applyFilterButtonClicked() {
        saleController.applyFilterButtonClicked(filterPanel);
    }

    private class SalePageButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object sourceOfAction = e.getSource();
            int selectedRow = getRow();
            if (sourceOfAction.equals(addSaleButton)) {
                saleController.addSaleButtonClicked();
            } else if (sourceOfAction.equals(removeSaleButton)) {
                saleController.removeSaleButtonClicked(selectedRow);
            } else if (sourceOfAction.equals(viewSaleButton)) {
                saleController.showSaleDetailsButtonClicked(selectedRow);
            }
        }

        private int getRow() {
            int selectedRow = saleTable.getSelectedRow();
            if (selectedRow != -1) { // Check whether any row is selected
                // Convert from [possibly] sorted view row index to underlying model row index
                selectedRow = tableSorter.convertRowIndexToModel(selectedRow);
            }
            return selectedRow;
        }
    }


}



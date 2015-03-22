package stat.ui.sale.view;

import stat.ui.Page;
import stat.ui.sale.SaleController;
import stat.ui.sale.view.helper.SaleColType;
import stat.ui.sale.view.helper.SaleTableModel;
import stat.ui.sale.view.helper.SaleTableSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Created by Eray Tuncer S000926 eray.tuncer@ozu.edu.tr
 */

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class SaleMainPage extends Page {

    @Autowired
    private SaleController saleController;

    @Autowired
    private SaleAddPage pageNewSale;

    private JTable saleTable;
    private ArrayList<Integer> saleIDList = new ArrayList<>();
    private JButton                saleRemoveButton;
    private SalePageButtonListener buttonListener;
    private JButton                addSaleButton;
    private JButton                viewSaleButton;
    private SaleTableModel         tableModel;
    private SaleTableSorter saleTableSorter;

    public SaleMainPage() {
        buttonListener = new SalePageButtonListener();
        initPage();
        initSaleTable();
        initButtons();
    }

    private void initPage() {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout(0, 0));
    }

    private void initSaleTable() {
        tableModel = new SaleTableModel();
//        saleTableSorter = new SaleTableSorter(tableModel);
        saleTable = new JTable(tableModel);
//        saleTable.setRowSorter(saleTableSorter);
        saleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(saleTable), BorderLayout.CENTER);
    }

    private void initButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 3, 0, 0));
        createRemoveButton(buttonPanel);
        createViewButton(buttonPanel);
        createAddButton(buttonPanel);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createRemoveButton(JPanel buttonPanel) {
        saleRemoveButton = new JButton("Delete Sale");
        saleRemoveButton.addActionListener(buttonListener);
        buttonPanel.add(saleRemoveButton);
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

    public void removeRow(int rowIndex) {
        int saleID = saleIDList.get(rowIndex);
        DefaultTableModel tableModel = (DefaultTableModel) saleTable.getModel();

        tableModel.removeRow(rowIndex);
        saleIDList.remove(rowIndex);
        saleController.removeSale(saleID);
    }

    public void refreshTable() {
        // Clear sales list
        emptySales();
        // Tell controller that sales list should be refreshed
        saleController.populateWithSales();
        // Display populated sales list
        this.repaint();
        this.validate();
    }

    public void emptySales() {
        DefaultTableModel tableModel = (DefaultTableModel) saleTable.getModel();
        tableModel.setRowCount(0);
        saleIDList.clear();
    }

    public void addSale(Integer id, String customerName, Date date, BigDecimal totalPrice) {
        DefaultTableModel tableModel = (DefaultTableModel) saleTable.getModel();
        Object[] saleRow = new Object[3];
        saleRow[0] = customerName;
        saleRow[1] = date;
        saleRow[2] = totalPrice;

        tableModel.addRow(saleRow);
        saleIDList.add(id);
    }

    private class SalePageButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object sourceOfAction = e.getSource();
            int rowIndex = saleTable.getSelectedRow();
            if (sourceOfAction.equals(saleRemoveButton)) {
                if (saleTable.getSelectedRowCount() > 0) {
                    SaleMainPage.this.removeRow(rowIndex);
                    SaleMainPage.this.refreshTable();
                }
            } else if (sourceOfAction.equals(addSaleButton)) {
                SaleMainPage.this.showPopup(pageNewSale);
                saleController.populateWithProductNames();
            } else if (sourceOfAction.equals(viewSaleButton)) {
                if (saleTable.getSelectedRowCount() > 0) {
                    SaleViewPage viewPage = new SaleViewPage();
                    viewPage.setSale(saleController.getSale(saleIDList.get(rowIndex)));
                    SaleMainPage.this.showPopup(viewPage);
                    saleController.fillSaleDetails(saleIDList.get(rowIndex));
                }
            }
        }
    }
}

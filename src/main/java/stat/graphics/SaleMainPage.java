package stat.graphics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import stat.controllers.SaleController;
import stat.domain.Sale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Eray Tuncer S000926 eray.tuncer@ozu.edu.tr
 */

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class SaleMainPage extends Page {

    @Autowired
    private SaleController saleController; // TODO: make use of

    @Autowired
    private SaleAddPage pageNewSale;

    private JTable saleTable;
    private ArrayList<Integer> saleIDList = new ArrayList<Integer>();

    public SaleMainPage() {
        initPage();
        initSaleTable();
        initButtons();
    }

    private void initPage() {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout(0, 0));
    }

    private void initSaleTable() {
        saleTable = new JTable(getSaleTableModel());
        saleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(saleTable);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    private TableModel getSaleTableModel() {
        // TODO: Remove static data
        Object[][] data = { {"Eray Tuncer", "11.11.1111", "599.99"},
                            {"Burcu sarikyaa", "11.11.1111", "599.99"}};
        String[] columnNames = {"Customer Name", "Date", "Total Price"};
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
        JButton buttonRemoveSale = new JButton("Delete Sale");
        buttonRemoveSale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (saleTable.getSelectedRowCount() > 0) {
                    int rowIndex = saleTable.getSelectedRow();
                    removeRow(rowIndex);
                }
            }
        });
        return buttonRemoveSale;
    }

    private JButton createButtonView() {
        JButton buttonViewSale = new JButton("View Sale");
        buttonViewSale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (saleTable.getSelectedRowCount() > 0) {
                    int rowIndex = saleTable.getSelectedRow();

                    SaleViewPage viewPage = new SaleViewPage();
                    viewPage.setSale(saleController.getSale(saleIDList.get(rowIndex)));
                    showPopup(viewPage);
                }
            }
        });
        return buttonViewSale;
    }

    private JButton createButtonAdd() {
        JButton buttonAddSale = new JButton("Add Sale");
        buttonAddSale.addActionListener(e -> {
            showPopup(pageNewSale);
            saleController.populateWithProductNames();
        });
        return buttonAddSale;
    }

    private void showPopup(Page page) {
        JFrame popupWindow = new JFrame();
        popupWindow.setContentPane(page);
        popupWindow.setSize(page.getSize());
        popupWindow.setResizable(false);
        popupWindow.setVisible(true);
    }

    public void emptySales() {
        DefaultTableModel tableModel = (DefaultTableModel) saleTable.getModel();
        int numberRows = tableModel.getRowCount();
        for (int rowIndex = 0; rowIndex < numberRows; rowIndex++) {
            tableModel.removeRow(rowIndex);
        }
        saleIDList = new ArrayList<Integer>();
    }

    public void removeRow(int rowIndex) {
        int saleID = saleIDList.get(rowIndex);
        DefaultTableModel tableModel = (DefaultTableModel) saleTable.getModel();

        tableModel.removeRow(rowIndex);
        saleIDList.remove(rowIndex);
        saleController.removeSale(saleID);
    }

    public void addSales(Set<Sale> saleSet) {
        saleSet.forEach(this::addSale);
    }

    public void addSale(Sale sale) {
        DefaultTableModel tableModel = (DefaultTableModel) saleTable.getModel();
        Object[] saleRow = new Object[3];
        saleRow[0] = sale.getCustomerName();
        saleRow[1] = sale.getDate();
        saleRow[2] = 0.0; // TODO: find way to calculate total amount

        tableModel.addRow(saleRow);
        saleIDList.add(sale.getSaleId());
    }

}

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
    private NewSalePage pageNewSale;

    private JTable saleTable;

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
        JScrollPane tableScrollPane = new JScrollPane(saleTable);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    private TableModel getSaleTableModel() {
        // TODO: Remove static data
        Object[][] data = {};
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
        // TODO: add Listener
        return buttonRemoveSale;
    }

    private JButton createButtonView() {
        JButton buttonRemoveSale = new JButton("View Sale");
        // TODO: add Listener
        return buttonRemoveSale;
    }

    private JButton createButtonAdd() {
        JButton buttonRemoveSale = new JButton("Add Sale");
        buttonRemoveSale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame popupWindow = new JFrame();
                popupWindow.setContentPane(pageNewSale);
                popupWindow.setSize(pageNewSale.getSize());
                popupWindow.setResizable(false);
                popupWindow.setVisible(true);
            }
        });
        return buttonRemoveSale;
    }

    public void emptySales() {
        DefaultTableModel tableModel = (DefaultTableModel) saleTable.getModel();
        int numberRows = tableModel.getRowCount();
        for (int i = 0; i < numberRows; i++) {
            tableModel.removeRow(i);
        }
    }

    public void addSales(Set<Sale> saleSet) {
        DefaultTableModel tableModel = (DefaultTableModel) saleTable.getModel();
        for (Sale sale : saleSet) {
            Object[] saleRow = new Object[3];
            saleRow[0] = sale.getCustomerName();
            saleRow[1] = sale.getDate();
            saleRow[2] = 0.0; // TODO: find way to calculate total amount

            tableModel.addRow(saleRow);
        }
    }

}

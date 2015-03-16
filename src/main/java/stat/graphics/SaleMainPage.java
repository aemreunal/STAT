package stat.graphics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import stat.controllers.SaleController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * Created by Eray Tuncer S000926 eray.tuncer@ozu.edu.tr
 */

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class SaleMainPage extends Page {

    @Autowired
    private SaleController saleController; // TODO: make use of

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
        Object[][] data = { {"Eray Tuncer"         , "16:07:1992", "199.99"},
                            {"Burcu Basak Sarikaya", "01:09:1992", "0.99"  }};
        //////////////////////////////////////////////////////////////////////
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
        // TODO: add Listener
        return buttonRemoveSale;
    }

}
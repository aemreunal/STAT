package stat.ui.product.detail.view.tabs;

/**
 * Author Burcu Basak SARIKAYA
 * S000855
 * burcu.sarikaya@ozu.edu.tr
 */

import stat.domain.Sale;
import stat.ui.Page;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import stat.ui.sale.main.view.helper.SaleTableModel;

import javax.swing.*;
import java.util.LinkedHashSet;

// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ProductSalesTab extends Page {

    private JTable saleTable;
    private SaleTableModel tableModel;

    public ProductSalesTab() {
        initPage();
        initSaleTable();
    }

    @Override
    protected void initPage() { setLayout(null); }

    private void initSaleTable() {
        tableModel = new SaleTableModel();
        saleTable = new JTable(tableModel);
        saleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(saleTable);
        scrollPane.setBounds(30, 30, 325, 200);
        add(scrollPane);
    }

    public void fillSaleTable(LinkedHashSet<Sale> sales) {
        for(Sale sale: sales) {
            tableModel.addRow(new Object[]{sale.getCustomerName(), sale.getDate(), sale.getTotalPrice()});
        }
    }
}

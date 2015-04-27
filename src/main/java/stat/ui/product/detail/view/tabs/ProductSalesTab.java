package stat.ui.product.detail.view.tabs;

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

import stat.domain.Sale;
import stat.ui.Page;
import stat.ui.sale.main.view.helper.SaleTableModel;

import java.util.LinkedHashSet;
import javax.swing.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ProductSalesTab extends Page {

    private JTable         saleTable;
    private SaleTableModel tableModel;

    public ProductSalesTab() {
        initPage();
        initSaleTable();
    }

    @Override
    protected void initPage() {
        setLayout(null);
    }

    private void initSaleTable() {
        tableModel = new SaleTableModel();
        saleTable = new JTable(tableModel);
        saleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(saleTable);
        scrollPane.setBounds(30, 30, 325, 200);
        add(scrollPane);
    }

    public void fillSaleTable(LinkedHashSet<Sale> sales) {
        for (Sale sale : sales) {
            tableModel.addRow(new Object[] { sale.getCustomerName(), sale.getDate(), sale.getTotalPrice() });
        }
    }
}

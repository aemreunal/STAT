package stat.ui.product.detail.view;

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

import stat.domain.Product;
import stat.domain.Sale;
import stat.ui.product.detail.ProductDetailController;
import stat.ui.product.detail.view.tabs.ProductInfoTab;
import stat.ui.product.detail.view.tabs.ProductSalesTab;

import java.awt.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ProductDetailWindow extends JFrame {

    public static final String TAB_INFO  = "Info";
    public static final String TAB_STATS = "Sales";

    private final Product                 product;
    private final ProductDetailController controller;

    private JPanel          contentPane;
    private ProductInfoTab  productInfoTab;
    private ProductSalesTab productSalesTab;

    public ProductDetailWindow(ProductDetailController controller, Product product) {
        this.controller = controller;
        this.product = product;
        initWindow();
        initTabs();
        initTabPane();
        setVisible(true);
    }

    private void initTabs() {
        productInfoTab = new ProductInfoTab(controller, product);
        productSalesTab = new ProductSalesTab();
    }

    private void initWindow() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(420, 370);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
    }

    private void initTabPane() {
        JTabbedPane pageTab = new JTabbedPane(JTabbedPane.TOP);
        pageTab.addTab(TAB_INFO, productInfoTab);
        pageTab.addTab(TAB_STATS, productSalesTab);

        contentPane.add(pageTab, BorderLayout.CENTER);
    }

    public void setProductSaleStats(int amountSold, BigDecimal totalRevenue) {
        productInfoTab.setAmountSold(amountSold);
        productInfoTab.setRevenue(totalRevenue);
    }

    public void setProductSales(LinkedHashSet<Sale> sales) {
        productSalesTab.fillSaleTable(sales);
    }
}

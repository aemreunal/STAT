package stat.ui.product.detail.view;

import stat.domain.Product;
import stat.ui.product.detail.ProductDetailController;
import stat.ui.product.detail.view.tabs.ProductInfoTab;
import stat.ui.product.detail.view.tabs.ProductSalesTab;

import java.awt.*;
import java.math.BigDecimal;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * Created by Burcu Basak SARIKAYA on 3/18/2015. S000855 burcu.sarikaya@ozu.edu.tr
 */

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
        productInfoTab = new ProductInfoTab(product);
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
}

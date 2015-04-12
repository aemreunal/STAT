package stat.ui.mainApp.view;

/*
 * ******************************* *
 * Copyright (c) 2015              *
 *                                 *
 * Sales Tracking & Analytics Tool *
 *                                 *
 * @author Ahmet Emre Ünal         *
 * @author Uğur Özkan              *
 * @author Burcu Başak Sarıkaya    *
 * @author Eray Tuncer             *
 *                                 *
 * ******************************* *
 */

import stat.ui.Page;
import stat.ui.product.main.view.ProductMainPage;
import stat.ui.sale.main.view.SaleMainPage;
import stat.ui.stats.main.view.StatMainPage;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ApplicationWindow extends JFrame {
    public static final String TAB_SALES   = "Sales";
    public static final String TAB_PRODUCT = "Product";
    public static final String TAB_STATS   = "Stats";

    @Autowired
    private SaleMainPage saleMainPage;

    @Autowired
    private ProductMainPage productMainPage;

    @Autowired
    private StatMainPage statMainPage;

    private JPanel      contentPane;
    private JTabbedPane tabbedPagePane;

    public ApplicationWindow() {
        initWindow();
        initTabs();
    }

    private void initWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 940, 600);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
    }

    private void initTabs() {
        tabbedPagePane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPagePane.addTab(TAB_SALES, new JPanel());
        tabbedPagePane.addTab(TAB_PRODUCT, new JPanel());
        tabbedPagePane.addTab(TAB_STATS, new JPanel());
        tabbedPagePane.addChangeListener(e -> {
            int index = ((JTabbedPane) e.getSource()).getSelectedIndex();
            ((Page) tabbedPagePane.getComponentAt(index)).refresh();
        });

        contentPane.add(tabbedPagePane, BorderLayout.CENTER);
    }

    public void display() {
        setVisible(true);
        tabbedPagePane.setComponentAt(tabbedPagePane.indexOfTab(TAB_SALES), saleMainPage);
        tabbedPagePane.setComponentAt(tabbedPagePane.indexOfTab(TAB_PRODUCT), productMainPage);
        tabbedPagePane.setComponentAt(tabbedPagePane.indexOfTab(TAB_STATS), statMainPage);
    }

}

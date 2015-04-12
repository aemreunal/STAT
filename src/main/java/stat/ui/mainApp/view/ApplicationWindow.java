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

import stat.ui.mainApp.ApplicationController;
import stat.ui.product.main.view.ProductMainPage;
import stat.ui.sale.main.view.SaleMainPage;
import stat.ui.stats.main.StatController;
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
    private ApplicationController applicationController;

    @Autowired
    private SaleMainPage saleMainPage;

    @Autowired
    private ProductMainPage productMainPage;

    @Autowired
    private StatMainPage statMainPage;

    @Autowired
    private StatController statController;

    private JPanel      contentPane;
    private JTabbedPane pageTab;

    public ApplicationWindow() {
        initWindow();
        initTabs();
    }

    private void initWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 940, 600);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
    }

    private void initTabs() {
        pageTab = new JTabbedPane(JTabbedPane.TOP);
        pageTab.addTab(TAB_SALES, new JPanel());
        pageTab.addTab(TAB_PRODUCT, new JPanel());
        pageTab.addTab(TAB_STATS, new JPanel());

        contentPane.add(pageTab, BorderLayout.CENTER);
    }

    public void display() {
        setVisible(true);
        pageTab.setComponentAt(pageTab.indexOfTab(TAB_SALES), saleMainPage);
        pageTab.setComponentAt(pageTab.indexOfTab(TAB_PRODUCT), productMainPage);
        pageTab.setComponentAt(pageTab.indexOfTab(TAB_STATS), statMainPage);

        statController.initializeYears();
    }

}

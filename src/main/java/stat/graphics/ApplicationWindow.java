package stat.graphics;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import stat.controllers.ApplicationController;

import java.awt.*;

/**
 * Created by Eray Tuncer S000926 eray.tuncer@ozu.edu.tr
 */

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ApplicationWindow extends JFrame {

    public static final String TAB_SALES   = "Sales";
    public static final String TAB_PRODUCT = "Product";

    @Autowired
    private ApplicationController applicationController;

    @Autowired
    private SaleMainPage saleMainPage;

    @Autowired
    private ProductMainPage productMainPage;

    private JPanel contentPane;
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
        pageTab.addTab(TAB_SALES  , new JPanel());
        pageTab.addTab(TAB_PRODUCT, new JPanel());
        pageTab.addChangeListener(createChangeListener());

        contentPane.add(pageTab, BorderLayout.CENTER);
    }

    private ChangeListener createChangeListener() {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JTabbedPane tab = (JTabbedPane) e.getSource();
                String tabName = tab.getTitleAt(tab.getSelectedIndex());
                applicationController.changedTab(tabName);
            }
        };
    }

    public void display() {
        setVisible(true);
        pageTab.setComponentAt(pageTab.indexOfTab(TAB_SALES), saleMainPage);
        pageTab.setComponentAt(pageTab.indexOfTab(TAB_PRODUCT), productMainPage);
    }

}

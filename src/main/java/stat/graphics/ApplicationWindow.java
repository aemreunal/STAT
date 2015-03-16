package stat.graphics;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import stat.controllers.PageController;
import stat.controllers.ProductController;
import stat.controllers.SaleController;

import java.awt.*;

/**
 * Created by Eray Tuncer S000926 eray.tuncer@ozu.edu.tr
 */

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ApplicationWindow extends JFrame {

    private static final String TAB_SALES   = "Sales";
    private static final String TAB_PRODUCT = "Product";

    @Autowired
    private ProductController productController;

    @Autowired
    private SaleController saleController;

    private JPanel contentPane;

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
        JTabbedPane pageTab = new JTabbedPane(JTabbedPane.TOP);
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

                PageController controller = getControllerByTabName(tabName);
                controller.refreshPage();
            }
        };
    }

    private PageController getControllerByTabName(String tabName) {
        if (tabName.equals(TAB_SALES)) {
            return saleController;
        } else if (tabName.equals(TAB_PRODUCT)) {
            return productController;
        } else {
            return null;
        }
    }

}

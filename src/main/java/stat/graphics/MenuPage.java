package stat.graphics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Created by Eray Tuncer S000926 eray.tuncer@ozu.edu.tr
 */

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class MenuPage extends Page {

    @Autowired
    private SaleAddPage newSalePage;

    @Autowired
    private ProductAddPage newProductPage;

    private JButton        createProductButton;
    private JButton        createSaleButton;
    private JButton        exitButton;
    private ButtonListener buttonListener;

    public MenuPage() {
        buttonListener = new ButtonListener();
        initPageDesign();
        initCreateProductButton();
        initSaleProductButton();
        initExitButton();
    }

    private void initPageDesign() {
        setSize(393, 320);
        setBackground(new Color(204, 204, 0));
        setLayout(null);
        setVisible(true);
    }

    private void initCreateProductButton() {
        createProductButton = new JButton("NEW PRODUCT");
        createProductButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        createProductButton.setForeground(new Color(0, 0, 0));
        createProductButton.setBackground(new Color(255, 255, 204));
        createProductButton.setBounds(70, 35, 250, 50);
        createProductButton.addActionListener(buttonListener);
        add(createProductButton);
    }

    private void initSaleProductButton() {
        createSaleButton = new JButton("NEW SALE");
        createSaleButton.setForeground(Color.BLACK);
        createSaleButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        createSaleButton.setBackground(new Color(255, 255, 204));
        createSaleButton.setBounds(70, 91, 250, 50);
        createSaleButton.addActionListener(buttonListener);
        add(createSaleButton);
    }

    private void initExitButton() {
        exitButton = new JButton("EXIT");
        exitButton.setForeground(Color.BLACK);
        exitButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        exitButton.setBackground(new Color(204, 51, 51));
        exitButton.setBounds(70, 222, 250, 50);
        exitButton.addActionListener(buttonListener);
        add(exitButton);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ApplicationWindow appWindow = MenuPage.this.getApplicationWindow();
            Object sourceOfAction = e.getSource();
            if (sourceOfAction instanceof JButton) {
                if (sourceOfAction.equals(createProductButton)) {
                    // TODO: change the functionality
                    //appWindow.setCurrentPage(newProductPage);
                } else if (sourceOfAction.equals(createSaleButton)) {
                    // TODO: change the functionality
                    //appWindow.setCurrentPage(newSalePage);
                } else if (sourceOfAction.equals(exitButton)) {
                    System.exit(0);
                }
            }
        }
    }
}

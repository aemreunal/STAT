package stat.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by
 * Eray Tuncer
 * S000926
 * eray.tuncer@ozu.edu.tr
 */

public class MenuPage extends Page {

    public MenuPage() {
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
        JButton buttonCreateProduct = new JButton("NEW PRODUCT");
        buttonCreateProduct.setFont(new Font("Tahoma", Font.BOLD, 15));
        buttonCreateProduct.setForeground(new Color(0, 0, 0));
        buttonCreateProduct.setBackground(new Color(255, 255, 204));
        buttonCreateProduct.setBounds(70, 35, 250, 50);
        buttonCreateProduct.addActionListener(getNextPageAction(new NewProductPage()));
        add(buttonCreateProduct);
    }

    private void initSaleProductButton() {
        JButton buttonSaleProduct = new JButton("SALE PRODUCT");
        buttonSaleProduct.setForeground(Color.BLACK);
        buttonSaleProduct.setFont(new Font("Tahoma", Font.BOLD, 15));
        buttonSaleProduct.setBackground(new Color(255, 255, 204));
        buttonSaleProduct.setBounds(70, 91, 250, 50);
        buttonSaleProduct.addActionListener(getNextPageAction(new NewProductPage())); // TODO: fix
        add(buttonSaleProduct);
    }

    private ActionListener getNextPageAction(Page nextPage) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ApplicationWindow appWindow = getApplicationWindow();
                appWindow.setCurrentPage(nextPage);
            }
        };
    }

    private void initExitButton() {
        JButton buttonExit = new JButton("EXIT");
        buttonExit.setForeground(Color.BLACK);
        buttonExit.setFont(new Font("Tahoma", Font.BOLD, 15));
        buttonExit.setBackground(new Color(204, 51, 51));
        buttonExit.setBounds(70, 222, 250, 50);
        buttonExit.addActionListener(getExitAction());
        add(buttonExit);
    }

    private ActionListener getExitAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
    }

}

package stat.ui.sale.main.view.helper;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ErayT on 03.04.2015.
 */
public class SaleFilter extends JPanel {

    private JTextField dateFromField;
    private JTextField dateToField;
    private JTextField priceFromField;
    private JTextField priceToField;

    public SaleFilter() {
        setLayout(new GridLayout(1, 4));
        add(initNameFilterHolder());
        add(initDateFilterHolder());
        add(initPriceFilterHolder());
    }

    private JPanel initNameFilterHolder() {
        JLabel nameLabel  = new JLabel("Name : ");
        JTextField nameField = new JTextField();
        nameField.setColumns(25);
        JPanel nameFilterHolder = new JPanel();
        nameFilterHolder.add(nameLabel);
        nameFilterHolder.add(nameField);

        return nameFilterHolder;
    }

    private JPanel initDateFilterHolder() {
        JLabel dateFromLabel = new JLabel("From : ");
        dateFromField = new JTextField();
        dateFromField.setColumns(25);
        JPanel dateFromHolder = new JPanel();
        dateFromHolder.add(dateFromLabel);
        dateFromHolder.add(dateFromField);

        JLabel dateToLabel   = new JLabel("To : ");
        dateToField = new JTextField();
        dateToField.setColumns(25);
        JPanel dateToHolder = new JPanel();
        dateToHolder.add(dateToLabel);
        dateToHolder.add(dateToField);

        JPanel dateFilterHolder = new JPanel();
        dateFilterHolder.setLayout(new GridLayout(2, 1));
        dateFilterHolder.add(dateFromHolder);
        dateFilterHolder.add(dateToHolder);

        return dateFilterHolder;
    }

    private JPanel initPriceFilterHolder() {
        JLabel priceFromLabel = new JLabel("From : ");
        priceFromField = new JTextField();
        priceFromField.setColumns(25);
        JPanel priceFromHolder = new JPanel();
        priceFromHolder.add(priceFromLabel);
        priceFromHolder.add(priceFromField);

        JLabel priceToLabel = new JLabel("To : ");
        priceToField = new JTextField();
        priceToField.setColumns(25);
        JPanel priceToHolder = new JPanel();
        priceToHolder.add(priceToLabel);
        priceToHolder.add(priceToField);

        JPanel priceFilterHolder = new JPanel();
        priceFilterHolder.setLayout(new GridLayout(2, 1));
        priceFilterHolder.add(priceFromHolder);
        priceFilterHolder.add(priceToHolder);

        return priceFilterHolder;
    }

}

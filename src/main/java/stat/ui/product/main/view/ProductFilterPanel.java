package stat.ui.product.main.view;

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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import javax.swing.*;

public class ProductFilterPanel extends JPanel {
    public static final int GAP_BETWEEN_FIELDS = 10;

    private final ProductMainPage     productMainPage;
    private final ApplyFilterListener filterListener;

    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField minPriceField;
    private JTextField maxPriceField;

    public ProductFilterPanel(ProductMainPage productMainPage) {
        this.productMainPage = productMainPage;
        filterListener = new ApplyFilterListener();
        setLayout(new GridLayout(1, 3, GAP_BETWEEN_FIELDS, 0));
        initNameHolder();
        initDescriptionHolder();
        initPriceHolder();
    }

    private void initNameHolder() {
        nameField = new JTextField();
        nameField.addActionListener(filterListener);
        JButton filterButton = new JButton("Apply filter...");
        filterButton.addActionListener(filterListener);

        JPanel nameFilterHolder = new JPanel(new GridBagLayout());
        addLabelAndComponent(nameFilterHolder, new JLabel("Name:"), this.nameField, 0);
        addLabelAndComponent(nameFilterHolder, null, filterButton, 1);
        add(nameFilterHolder);
    }

    private void initDescriptionHolder() {
        descriptionField = new JTextField();
        descriptionField.addActionListener(filterListener);

        JPanel descriptionFilterHolder = new JPanel(new GridBagLayout());
        addLabelAndComponent(descriptionFilterHolder, new JLabel("Desc:"), descriptionField, 0);
        add(descriptionFilterHolder);
    }

    private void initPriceHolder() {
        minPriceField = new JTextField();
        minPriceField.addActionListener(filterListener);
        maxPriceField = new JTextField();
        maxPriceField.addActionListener(filterListener);

        JPanel priceFilterHolder = new JPanel(new GridBagLayout());
        addLabelAndComponent(priceFilterHolder, new JLabel("Min:"), minPriceField, 0);
        addLabelAndComponent(priceFilterHolder, new JLabel("Max:"), maxPriceField, 1);
        add(priceFilterHolder);
    }

    private void addLabelAndComponent(JPanel panel, JLabel label, JComponent component, int row) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridy = row;
        constraints.weighty = 1.0;

        if (label != null) {
            constraints.fill = GridBagConstraints.NONE;
            constraints.gridx = 0;
            constraints.weightx = 0.0;
            panel.add(label, constraints);
        }

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.weightx = 1.0;
        panel.add(component, constraints);
    }

    public String getProductName() {
        return nameField.getText().trim();
    }

    public String getDescription() {
        return descriptionField.getText().trim();
    }

    public BigDecimal getMinPrice() {
        try {
            return new BigDecimal(minPriceField.getText().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public BigDecimal getMaxPrice() {
        try {
            return new BigDecimal(maxPriceField.getText().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private class ApplyFilterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            productMainPage.applyFilterButtonClicked();
        }
    }

}

package stat.ui.sale.main.view;

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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import javax.swing.*;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class SaleFilterPanel extends JPanel {
    public static final int GAP_BETWEEN_FIELDS = 10;
    private final SaleMainPage saleMainPage;

    private JTextField nameField;

    protected UtilDateModel fromDateModel;
    protected UtilDateModel untilDateModel;

    private JTextField minPriceField;
    private JTextField maxPriceField;

    private JButton filterButton;
    private ApplyFilterListener filterListener;

    public SaleFilterPanel(SaleMainPage saleMainPage) {
        this.saleMainPage = saleMainPage;
        filterListener = new ApplyFilterListener();
        setLayout(new GridLayout(1, 3, GAP_BETWEEN_FIELDS, 0));
        initNameFilterHolder();
        initDateFilterHolder();
        initPriceFilterHolder();
    }

    private void initNameFilterHolder() {
        nameField = new JTextField();
        nameField.addActionListener(filterListener);
        filterButton = new JButton("Apply filter...");
        filterButton.addActionListener(filterListener);

        JPanel nameFilterHolder = new JPanel(new GridBagLayout());
        addLabelAndComponent(nameFilterHolder, new JLabel("Name:"), this.nameField, 0);
        addLabelAndComponent(nameFilterHolder, null, this.filterButton, 1);

        add(nameFilterHolder);
    }

    private void initDateFilterHolder() {
        JDatePickerImpl fromDatePicker = Page.createDatePicker();
        fromDateModel = (UtilDateModel) fromDatePicker.getModel();
        
        JDatePickerImpl untilDatePicker = Page.createDatePicker();
        untilDateModel = (UtilDateModel) untilDatePicker.getModel();

        JPanel dateFilterHolder = new JPanel(new GridBagLayout());
        addLabelAndComponent(dateFilterHolder, new JLabel("From:"), fromDatePicker, 0);
        addLabelAndComponent(dateFilterHolder, new JLabel("Until:"), untilDatePicker, 1);

        add(dateFilterHolder);
    }

    private void initPriceFilterHolder() {
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

    public String getCustomerName() {
        return nameField.getText().trim();
    }

    public Date getFromDate() {
        return fromDateModel.getValue();
    }

    public Date getUntilDate() {
        return untilDateModel.getValue();
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
            saleMainPage.applyFilterButtonClicked();
        }
    }
}

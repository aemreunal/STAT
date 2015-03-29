package stat.ui.product.detail.view.tabs;

/*
 ***************************
 * Copyright (c) 2015      *
 *                         *
 * This code belongs to:   *
 *                         *
 * @author Ahmet Emre Ünal *
 * S001974                 *
 *                         *
 * aemreunal@gmail.com     *
 * emre.unal@ozu.edu.tr    *
 *                         *
 * aemreunal.com           *
 ***************************
 */

import stat.domain.Product;
import stat.ui.Page;

import java.awt.*;
import java.math.BigDecimal;
import javax.swing.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ProductInfoTab extends Page {

    private JPanel     fieldHolder;
    private JTextField revenueField;
    private JTextField amountField;

    public ProductInfoTab(Product product) {
        initPage();
        initFields(product);
    }

    @Override
    protected void initPage() {
        setLayout(null);
    }

    private void initFields(Product product) {
        initFieldHolder();
        initNameField(product.getName());
        initDescriptionField(product.getDescription());
        initPriceField(String.valueOf(product.getPrice()));
        initAmountSoldField();
        initProductRevenueField();
    }

    private void initFieldHolder() {
        fieldHolder = new JPanel();
        fieldHolder.setBounds(50, 25, 311, 165);
        fieldHolder.setLayout(null);
        add(fieldHolder);
    }

    private void initNameField(String productName) {
        JLabel nameLabel = new JLabel("Name :");
        nameLabel.setBounds(6, 17, 108, 19);
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JTextField nameField = new JTextField(productName);
        nameField.setBounds(124, 16, 181, 20);
        nameField.setColumns(10);
        nameField.setEditable(false);

        fieldHolder.add(nameLabel);
        fieldHolder.add(nameField);
    }

    private void initDescriptionField(String description) {
        JLabel descriptionLabel = new JLabel("Description :");
        descriptionLabel.setBounds(6, 42, 108, 19);
        descriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JTextField descriptionField = new JTextField(description);
        descriptionField.setBounds(124, 41, 181, 20);
        descriptionField.setColumns(10);
        descriptionField.setEditable(false);

        fieldHolder.add(descriptionLabel);
        fieldHolder.add(descriptionField);
    }

    private void initPriceField(String price) {
        JLabel priceLabel = new JLabel("Unit Price :");
        priceLabel.setBounds(6, 66, 108, 19);
        priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JTextField priceField = new JTextField(price);
        priceField.setBounds(124, 65, 181, 20);
        priceField.setColumns(10);
        priceField.setEditable(false);

        fieldHolder.add(priceLabel);
        fieldHolder.add(priceField);
    }

    private void initAmountSoldField() {
        JLabel amountLabel = new JLabel("Amount Sold:");
        amountLabel.setBounds(6, 90, 108, 19);
        amountLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

        amountField = new JTextField();
        amountField.setBounds(124, 89, 181, 20);
        amountField.setColumns(10);
        amountField.setEditable(false);

        fieldHolder.add(amountLabel);
        fieldHolder.add(amountField);
    }

    private void initProductRevenueField() {
        JLabel revenueLabel = new JLabel("Total Revenue:");
        revenueLabel.setBounds(6, 114, 108, 19);
        revenueLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

        revenueField = new JTextField();
        revenueField.setBounds(124, 113, 181, 20);
        revenueField.setColumns(10);
        revenueField.setEditable(false);

        fieldHolder.add(revenueLabel);
        fieldHolder.add(revenueField);
    }

    public void setAmountSold(int amountSold) {
        this.amountField.setText(String.valueOf(amountSold));
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenueField.setText(revenue.toPlainString());
    }
}

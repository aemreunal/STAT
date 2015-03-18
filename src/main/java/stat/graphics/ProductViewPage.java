package stat.graphics;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by Burcu Basak SARIKAYA on 3/18/2015.
 * S000855
 * burcu.sarikaya@ozu.edu.tr
 */

@org.springframework.stereotype.Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ProductViewPage extends Page {

    private JPanel fieldHolder;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField priceField;

    public ProductViewPage() {
        initializePageDesign();
        initializeFields();
    }

    private void initializePageDesign() {
        setSize(420, 370);
        setBackground(new Color(204, 204, 0));
        setLayout(null);
    }

    private void initializeFields() {
        initializeFieldHolder();
        initializeNameField();
        initializeDescriptionField();
        initializePriceField();
    }

    private void initializeFieldHolder() {
        fieldHolder = new JPanel();
        fieldHolder.setBackground(new Color(245, 245, 220));
        fieldHolder.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Product Info", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        fieldHolder.setBounds(50, 25, 311, 165);
        fieldHolder.setLayout(null);
        add(fieldHolder);
    }

    private void initializeNameField() {
        JLabel nameLabel = new JLabel("Name :");
        nameLabel.setBounds(6, 17, 108, 19);
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

        nameField = new JTextField();
        nameField.setBounds(124, 16, 181, 20);
        nameField.setColumns(10);
        nameField.setEditable(false);

        fieldHolder.add(nameLabel);
        fieldHolder.add(nameField);
    }

    private void initializeDescriptionField() {
        JLabel descriptionLabel = new JLabel("Description :");
        descriptionLabel.setBounds(6, 42, 108, 19);
        descriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

        descriptionField = new JTextField();
        descriptionField.setBounds(124, 41, 181, 20);
        descriptionField.setColumns(10);
        descriptionField.setEditable(false);

        fieldHolder.add(descriptionLabel);
        fieldHolder.add(descriptionField);
    }

    private void initializePriceField() {
        JLabel priceLabel = new JLabel("Unit Price :");
        priceLabel.setBounds(6, 66, 108, 19);
        priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

        priceField = new JTextField();
        priceField.setBounds(124, 65, 181, 20);
        priceField.setColumns(10);
        priceField.setEditable(false);

        fieldHolder.add(priceLabel);
        fieldHolder.add(priceField);
    }

    public void setFields(String productName, String description, String unitPrice){
        nameField.setText(productName);
        descriptionField.setText(description);
        priceField.setText(unitPrice);
    }

    public void clearFields() {
        setFields("", "", "");
    }

}

package stat.ui.product.view;

import stat.domain.Product;
import stat.domain.Sale;
import stat.ui.Page;

import java.awt.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * Created by Burcu Basak SARIKAYA on 3/18/2015. S000855 burcu.sarikaya@ozu.edu.tr
 */

// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ProductViewPage extends Page {

    private JPanel     fieldHolder;

    public ProductViewPage(Product product) {
        initPage();
        initializeFields(product);
    }

    protected void initPage() {
        setSize(420, 370);
        setBackground(new Color(204, 204, 0));
        setLayout(null);
    }

    private void initializeFields(Product product) {
        initializeFieldHolder();
        initializeNameField(product.getName());
        initializeDescriptionField(product.getDescription());
        initializePriceField(String.valueOf(product.getPrice()));
    }

    private void initializeFieldHolder() {
        fieldHolder = new JPanel();
        fieldHolder.setBackground(new Color(245, 245, 220));
        fieldHolder.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Product Info", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        fieldHolder.setBounds(50, 25, 311, 165);
        fieldHolder.setLayout(null);
        add(fieldHolder);
    }

    private void initializeNameField(String productName) {
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

    private void initializeDescriptionField(String description) {
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

    private void initializePriceField(String price) {
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

    public void initializeDetails(String amountOfProductSoldTotal, String priceOfProductSoldTotal, LinkedHashSet<Sale> sales) {
        //TODO initialize amountOfProductSoldTotal and priceOfProductSoldTotal fields, and sales
    }
}

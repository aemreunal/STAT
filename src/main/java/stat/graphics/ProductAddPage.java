package stat.graphics;

import stat.controllers.ProductController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Created by Eray Tuncer S000926 eray.tuncer@ozu.edu.tr
 */

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ProductAddPage extends Page {

    @Autowired
    private ProductController productController;

    private JPanel     fieldHolder;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JButton    saveButton;
    private JButton    backButton;

    private ButtonListener buttonListener;

    public ProductAddPage() {
        buttonListener = new ButtonListener();
        initializePageDesign();
        initializeFields();
        initializeButtons();
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

        fieldHolder.add(priceLabel);
        fieldHolder.add(priceField);
    }

    private void initializeButtons() {
        initializeSaveButton();
        initializeBackButton();
    }

    private void initializeSaveButton() {
        saveButton = new JButton("SAVE");
        saveButton.setForeground(new Color(0, 153, 51));
        saveButton.setBackground(new Color(245, 245, 245));
        saveButton.setBounds(50, 210, 311, 50);
        saveButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        saveButton.addActionListener(buttonListener);

        add(saveButton);
    }

    private void initializeBackButton() {
        backButton = new JButton("Back");
        backButton.setForeground(new Color(204, 51, 51));
        backButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        backButton.setBackground(new Color(245, 245, 245));
        backButton.setBounds(50, 263, 311, 50);
        backButton.addActionListener(buttonListener);

        add(backButton);
    }

    public void clearInputFields() {
        nameField.setText(null);
        descriptionField.setText(null);
        priceField.setText(null);
    }

    private boolean validateFields() {
        String regex = "^([0-9]+)([.][0-9]{1,4})?$";
        String price = priceField.getText();
        // TODO: implement
        boolean valid = true;
        valid &= (nameField.getText().length() > 0);
        valid &= (descriptionField.getText().length() > 0);
        valid &= price.matches(regex);
        return valid;
    }

    private void saveProduct() {
        String productName = nameField.getText();
        String productDescription = descriptionField.getText();
        BigDecimal productPrice = new BigDecimal(priceField.getText());
        productController.saveProduct(productName, productDescription, productPrice);
    }

    public void displaySuccess() {
        JOptionPane.showMessageDialog(this, "The Product was successfully saved.");
    }

    public void displayValidationError() {
        JOptionPane.showMessageDialog(this,
                                      "The fields have invalid data. Please check them again.",
                                      "Validation Error",
                                      JOptionPane.ERROR_MESSAGE);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ApplicationWindow appWindow = getApplicationWindow();
            Object sourceOfAction = e.getSource();
            if (sourceOfAction instanceof JButton) {
                if (sourceOfAction.equals(saveButton)) {
                    if (validateFields()) {
                        saveProduct();
                        //TODO close maybe?
                    } else {
                        displayValidationError();
                        //TODO ask again
                    }
                } else if (sourceOfAction.equals(backButton)) {
                    // TODO: change the functionality
                    //appWindow.setCurrentPage(menuPage);
                }
            }
        }
    }

}

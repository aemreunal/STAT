package stat.ui.product.view;

import stat.ui.Page;
import stat.ui.product.ProductController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * Created by Eray Tuncer S000926 eray.tuncer@ozu.edu.tr
 */

// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class ProductAddPage extends Page {

    private ProductController productController;

    private JPanel     fieldHolder;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JButton    saveButton;
    private JButton    backButton;

    private ButtonListener buttonListener;

    public ProductAddPage(ProductController productController) {
        this.productController = productController;
        buttonListener = new ButtonListener();
        initPage();
        initializeFields();
        initializeButtons();
    }

    protected void initPage() {
        setMinimumSize(new Dimension(420, 370));
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
        saveButton = new JButton("Create");
        saveButton.setBounds(50, 210, 311, 30);
        saveButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        saveButton.addActionListener(buttonListener);

        add(saveButton);
    }

    private void initializeBackButton() {
        backButton = new JButton("Cancel");
        backButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        backButton.setBounds(50, 263, 311, 30);
        backButton.addActionListener(buttonListener);

        add(backButton);
    }

    public void clearInputFields() {
        nameField.setText(null);
        descriptionField.setText(null);
        priceField.setText(null);
    }

    private boolean fieldsAreValid() {
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
        String productName = nameField.getText().trim();
        String productDescription = descriptionField.getText().trim();
        BigDecimal productPrice = new BigDecimal(priceField.getText());
        productController.saveProduct(this, productName, productDescription, productPrice);
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

    public void displayNameError() {
        JOptionPane.showMessageDialog(this,
                                      "There already is a product named \'" + nameField.getText().trim() + "\', please enter a different name.",
                                      "Validation Error",
                                      JOptionPane.ERROR_MESSAGE);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object sourceOfAction = e.getSource();
            if (sourceOfAction.equals(saveButton)) {
                if (fieldsAreValid()) {
                    saveProduct();
                    //TODO close maybe?
                } else {
                    displayValidationError();
                    //TODO ask again
                }
            } else if (sourceOfAction.equals(backButton)) {
                JFrame parentFrame = (JFrame) getRootPane().getParent();
                parentFrame.dispose();
            }
        }
    }
}

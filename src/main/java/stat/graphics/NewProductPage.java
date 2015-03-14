package stat.graphics;

import org.springframework.beans.factory.annotation.Autowired;
import stat.service.ProductService;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

/**
 * Created by
 * Eray Tuncer
 * S000926
 * eray.tuncer@ozu.edu.tr
 */

public class NewProductPage extends Page {

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    protected ProductService productService;

    private JPanel fieldHolder;

    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField priceField;

    public NewProductPage() {
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
        JButton buttonSave = new JButton("SAVE");
        buttonSave.setForeground(new Color(0, 153, 51));
        buttonSave.setBackground(new Color(245, 245, 245));
        buttonSave.setBounds(50, 210, 311, 50);
        buttonSave.setFont(new Font("Tahoma", Font.BOLD, 16));
        buttonSave.addActionListener(getSaveAction());

        add(buttonSave);
    }

    private void initializeBackButton() {
        JButton buttonBack = new JButton("Back");
        buttonBack.setForeground(new Color(204, 51, 51));
        buttonBack.setFont(new Font("Tahoma", Font.BOLD, 16));
        buttonBack.setBackground(new Color(245, 245, 245));
        buttonBack.setBounds(50, 263, 311, 50);
        buttonBack.addActionListener(getBackAction());

        add(buttonBack);
    }

    private ActionListener getSaveAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    saveProduct();
                    displaySuccess();
                } else {
                    displayValidationError();
                }
            }
        };
    }

    private boolean validateFields() {
        String price = priceField.getText();
        // TODO: implement
        boolean valid = true;
        valid &= nameField.getText().length() > 0;
        valid &= descriptionField.getText().length() > 0;
        valid &= price.length() > 0;
        valid &= (price.contains("."))? price.substring(price.indexOf(".") + 1).length() <= 4 : true;
        return valid;
    }

    private void saveProduct() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        BigDecimal price = new BigDecimal(priceField.getText());

        ///////// TODO: REMOVE ////////
        System.out.println("Product Saved:");
        System.out.println(name);
        System.out.println(description);
        System.out.println(price);
        ///////////////////////////////


//        try {
//            productService.createNewProduct(name, description, price);
//        } catch (ProductNameException e) {
//            // TODO: inform user
//            e.printStackTrace();
//        }

    }

    private void displaySuccess() {
        JOptionPane.showMessageDialog(this, "The Product successfully saved.");
    }

    private void displayValidationError() {
        JOptionPane.showMessageDialog(this,
                "Enter the fields correctly.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private ActionListener getBackAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ApplicationWindow appWindow = getApplicationWindow();
                appWindow.setCurrentPage(new MenuPage());
            }
        };
    }

}

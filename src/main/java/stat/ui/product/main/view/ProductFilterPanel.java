package stat.ui.product.main.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Eray Tuncer S000926 eray.tuncer@ozu.edu.tr
 */

public class ProductFilterPanel extends JPanel {

    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField minPriceField;
    private JTextField maxPriceField;

    public ProductFilterPanel() {
        setLayout(new GridLayout(1, 3, 10, 0));
        initNameHolder();
        initDescriptionHolder();
        initPriceHolder();
    }

    private void initNameHolder() {
        nameField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        JButton filterButton = new JButton("Apply Filter");


        JPanel nameHolder = new JPanel(new GridBagLayout());
        addLabel(nameHolder, nameLabel, 0);
        addField(nameHolder, nameField, 0);
        addButton(nameHolder, filterButton, 1);
        add(nameHolder);
    }

    private void initDescriptionHolder() {
        descriptionField = new JTextField();
        JLabel descriptionLabel = new JLabel("Desc:");

        JPanel descriptionHolder = new JPanel(new GridBagLayout());
        addLabel(descriptionHolder, descriptionLabel, 0);
        addField(descriptionHolder, descriptionField, 0);
        addField(descriptionHolder, new JPanel(), 1); // dummy jpanel to lift the filter up
        add(descriptionHolder);
    }

    private void initPriceHolder() {
        minPriceField = new JTextField();
        maxPriceField = new JTextField();

        JLabel minLabel = new JLabel("Min:");
        JLabel maxLabel = new JLabel("Max:");

        JPanel priceHolder = new JPanel(new GridBagLayout());
        addLabel(priceHolder, minLabel, 0);
        addField(priceHolder, minPriceField, 0);
        addLabel(priceHolder, maxLabel, 1);
        addField(priceHolder, maxPriceField, 1);
        add(priceHolder);
    }

    private void addLabel(JPanel holder, Component label, int rowIndex) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.0;
        constraints.weighty = 1.0;
        constraints.gridx = 0;
        constraints.gridy = rowIndex;
        holder.add(label, constraints);
    }

    private void addField(JPanel holder, Component field, int rowIndex) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridx = 1;
        constraints.gridy = rowIndex;
        holder.add(field, constraints);
    }

    private void addButton(JPanel holder, Component button, int rowIndex) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridx = 1;
        constraints.gridy = rowIndex;
        holder.add(button, constraints);
    }

}

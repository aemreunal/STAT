package stat.ui.sale.main.view;

import stat.ui.Page;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Date;
import javax.swing.*;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class SaleFilterPanel extends JPanel {
    public static final int HGAP = 10;

    private JTextField nameField;

    protected UtilDateModel fromDateModel;
    protected UtilDateModel untilDateModel;

    private JTextField minPriceField;
    private JTextField maxPriceField;

    private JButton filterButton;

    public SaleFilterPanel() {
        setLayout(new GridLayout(1, 3, HGAP, 0));
        initNameFilterHolder();
        initDateFilterHolder();
        initPriceFilterHolder();
    }

    private void initNameFilterHolder() {
        nameField = new JTextField();
        filterButton = new JButton("Apply filter...");

        JPanel nameFilterHolder = new JPanel(new GridBagLayout());
        addLabelAndComponent(nameFilterHolder, new JLabel("Name:"), this.nameField, 0);
        addLabelAndComponent(nameFilterHolder, null, this.filterButton, 1);

        add(nameFilterHolder);
    }

    private void initDateFilterHolder() {
        JDatePickerImpl fromDatePicker = Page.createDatePicker(fromDateModel);
        JDatePickerImpl untilDatePicker = Page.createDatePicker(untilDateModel);

        JPanel dateFilterHolder = new JPanel(new GridBagLayout());
        addLabelAndComponent(dateFilterHolder, new JLabel("From:"), fromDatePicker, 0);
        addLabelAndComponent(dateFilterHolder, new JLabel("Until:"), untilDatePicker, 1);

        add(dateFilterHolder);
    }

    private void initPriceFilterHolder() {
        minPriceField = new JTextField();
        maxPriceField = new JTextField();

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

}

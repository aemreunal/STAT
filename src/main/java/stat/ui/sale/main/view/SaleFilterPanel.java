package stat.ui.sale.main.view;

import stat.ui.Page;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Date;
import javax.swing.*;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class SaleFilterPanel extends JPanel {

    private JTextField nameField;

    protected UtilDateModel fromDateModel;
    protected UtilDateModel untilDateModel;

    private JTextField minPriceField;
    private JTextField maxPriceField;

    public SaleFilterPanel() {
        setLayout(new GridLayout(1, 4));
        add(initNameFilterHolder());
        add(initDateFilterHolder());
        add(initPriceFilterHolder());
    }

    private JPanel initNameFilterHolder() {
        JPanel nameFilterHolder = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1.0;
        c.weightx = 1.0;
        nameFilterHolder.add(new JLabel("Name:"));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 1;
        nameField = new JTextField();
        nameFilterHolder.add(nameField, c);

        return nameFilterHolder;
    }

    private JPanel initDateFilterHolder() {
        JDatePickerImpl fromDatePicker = Page.createDatePicker(fromDateModel);
        JPanel fromDateHolder = new JPanel();
        fromDateHolder.add(new JLabel("From:"));
        fromDateHolder.add(fromDatePicker);

        JDatePickerImpl untilDatePicker = Page.createDatePicker(untilDateModel);
        JPanel untilDateHolder = new JPanel();
        untilDateHolder.add(new JLabel("Until:"));
        untilDateHolder.add(untilDatePicker);

        JPanel dateFilterHolder = new JPanel();
        dateFilterHolder.setLayout(new GridLayout(2, 1));
        dateFilterHolder.add(fromDateHolder);
        dateFilterHolder.add(untilDateHolder);

        return dateFilterHolder;
    }

    private JPanel initPriceFilterHolder() {
        minPriceField = new JTextField();
        JPanel minPriceHolder = new JPanel(new BorderLayout());
        minPriceHolder.add(new JLabel("Min:"), BorderLayout.WEST);
        minPriceHolder.add(minPriceField, BorderLayout.CENTER);

        maxPriceField = new JTextField();
        JPanel maxPriceHolder = new JPanel(new BorderLayout());
        maxPriceHolder.add(new JLabel("Max:"), BorderLayout.WEST);
        maxPriceHolder.add(maxPriceField, BorderLayout.CENTER);

        JPanel priceFilterHolder = new JPanel(new GridLayout(2, 1));
        priceFilterHolder.add(minPriceHolder);
        priceFilterHolder.add(maxPriceHolder);

        return priceFilterHolder;
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

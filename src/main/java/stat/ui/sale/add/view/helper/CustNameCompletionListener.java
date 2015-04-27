package stat.ui.sale.add.view.helper;

/*
 * ******************************* *
 * Copyright (c) 2015              *
 *                                 *
 * Sales Tracking & Analytics Tool *
 *                                 *
 * @author Ahmet Emre Ünal         *
 * @author Uğur Özkan              *
 * @author Burcu Başak Sarıkaya    *
 * @author Eray Tunçer             *
 *                                 *
 * ******************************* *
 */

import stat.ui.sale.add.SaleAddController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CustNameCompletionListener implements DocumentListener {

    private Runnable          doHighlight;
    private JTextField        customerNameField;
    private SaleAddController saleAddController;

    public CustNameCompletionListener(SaleAddController saleAddController, JTextField customerNameField) {
        this.saleAddController = saleAddController;
        this.customerNameField = customerNameField;
        doHighlight = new CustNameCompletionListener.CustNameTextHighlighter();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        SwingUtilities.invokeLater(doHighlight);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        // Do nothing
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        // Do nothing
    }

    private class CustNameTextHighlighter implements Runnable {
        @Override
        public void run() {
            String currentText = customerNameField.getText();
            String suggestion = saleAddController.getNameSuggestion(currentText);
            if (!suggestion.equals(customerNameField.getText()) && suggestion.length() > currentText.length()) {
                customerNameField.setText(suggestion);
                customerNameField.setSelectionStart(currentText.length());
                customerNameField.setSelectionEnd(suggestion.length());
            }
        }
    }
}

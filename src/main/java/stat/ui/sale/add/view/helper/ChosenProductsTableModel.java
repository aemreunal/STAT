package stat.ui.sale.add.view.helper;

/*
 ***************************
 * Copyright (c) 2015      *
 *                         *
 * This code belongs to:   *
 *                         *
 * @author Ahmet Emre Ünal *
 * S001974                 *
 *                         *
 * emre@aemreunal.com      *
 * emre.unal@ozu.edu.tr    *
 *                         *
 * aemreunal.com           *
 ***************************
 */

import stat.ui.sale.add.view.SaleAddPage;

import javax.swing.table.DefaultTableModel;

public class ChosenProductsTableModel extends DefaultTableModel {
    public static final String[] CHOSEN_PRODUCTS_TABLE_COLUMN_NAMES = new String[] { "Product", "Amount", "Price" };
    private final SaleAddPage saleAddPage;

    public ChosenProductsTableModel(SaleAddPage saleAddPage) {
        super(new Object[][] { }, CHOSEN_PRODUCTS_TABLE_COLUMN_NAMES);
        this.saleAddPage = saleAddPage;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return (column == 1); // Make the first column editable
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        if (column == 1) {
            String regex = "[0-9]+";
            String value = (String) aValue;
            if (value.matches(regex)) {
                super.setValueAt(aValue, row, column);
                saleAddPage.productAmountChanged(row, Integer.parseInt(value));
            }
        } else {
            super.setValueAt(aValue, row, column);
        }
    }

}

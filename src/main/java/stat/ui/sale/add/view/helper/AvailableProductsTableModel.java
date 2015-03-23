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

import javax.swing.table.DefaultTableModel;

public class AvailableProductsTableModel extends DefaultTableModel {
    public static final String[] AVAILABLE_PRODUCTS_TABLE_COLUMN_NAMES = new String[] { "Product" };

    public AvailableProductsTableModel() {
        super(new Object[][] { }, AVAILABLE_PRODUCTS_TABLE_COLUMN_NAMES);
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

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

import javax.swing.table.DefaultTableModel;

public class AvailableProductsTableModel extends DefaultTableModel {
    public static final String[] AVAILABLE_PRODUCTS_TABLE_COLUMN_NAMES = new String[] { "Product" };

    public AvailableProductsTableModel() {
        super(new Object[][] {}, AVAILABLE_PRODUCTS_TABLE_COLUMN_NAMES);
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

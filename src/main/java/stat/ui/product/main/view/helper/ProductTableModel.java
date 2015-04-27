package stat.ui.product.main.view.helper;

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

public class ProductTableModel extends DefaultTableModel {
    public ProductTableModel() {
        super(new Object[0][0], ProductColType.getColNameList());
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return ProductColType.getColClass(columnIndex);
    }
}

package stat.ui.sale.main.view.helper;

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

public class SaleTableModel extends DefaultTableModel {
    public SaleTableModel() {
        super(new Object[0][0], SaleColType.getColNameList());
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return SaleColType.getColClass(columnIndex);
    }
}

package stat.ui.sale.view.helper;

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

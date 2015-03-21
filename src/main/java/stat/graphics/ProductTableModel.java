package stat.graphics;

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

public class ProductTableModel extends DefaultTableModel {
    public ProductTableModel(Object[][] data, String[] columnNames) {
        super(data, columnNames);
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return ProductColType.getColClass(columnIndex);
    }
}

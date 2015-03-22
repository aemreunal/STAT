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

import java.math.BigDecimal;
import java.util.Date;

public enum SaleColType {
    NAME(0, "Customer Name", String.class), DATE(1, "Date of Sale", Date.class), PRICE(2, "Total Price", BigDecimal.class);

    private final int    colIndex;
    private final String colName;
    private final Class  colClass;

    SaleColType(int colIndex, String colName, Class colClass) {
        this.colIndex = colIndex;
        this.colName = colName;
        this.colClass = colClass;
    }

    public int getColIndex() {
        return colIndex;
    }

    public String getColName() {
        return colName;
    }

    public Class getColClass() {
        return colClass;
    }

    public static String[] getColNameList() {
        SaleColType[] values = SaleColType.values();
        String[] colNames = new String[values.length];
        for (SaleColType type : values) {
            colNames[type.getColIndex()] = type.getColName();
        }
        return colNames;
    }

    public static SaleColType fromColIndex(int colIndex) {
        for (SaleColType type : SaleColType.values()) {
            if (type.getColIndex() == colIndex) {
                return type;
            }
        }
        return null;
    }

    public static Class<?> getColClass(int columnIndex) {
        return SaleColType.fromColIndex(columnIndex).getColClass();
    }

    public static int getNumCols() {
        return values().length;
    }
}

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

public enum ProductColType {
    NAME(0, "Product Name", String.class), DESCRIPTION(1, "Description", String.class), PRICE(2, "Unit Price", Double.class);

    private final int    colIndex;
    private final String colName;
    private final Class  colClass;

    ProductColType(int colIndex, String colName, Class colClass) {
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
        ProductColType[] values = ProductColType.values();
        String[] colNames = new String[values.length];
        for (ProductColType type : values) {
            colNames[type.getColIndex()] = type.getColName();
        }
        return colNames;
    }

    public static ProductColType fromColIndex(int colIndex) {
        for (ProductColType type : ProductColType.values()) {
            if (type.getColIndex() == colIndex) {
                return type;
            }
        }
        return null;
    }

    public static Class<?> getColClass(int columnIndex) {
        return ProductColType.fromColIndex(columnIndex).getColClass();
    }
}

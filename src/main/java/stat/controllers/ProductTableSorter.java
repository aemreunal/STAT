package stat.controllers;

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

import stat.domain.Product;
import stat.graphics.ProductColType;

import java.util.Comparator;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class ProductTableSorter extends TableRowSorter {

    public ProductTableSorter(TableModel model) {
        super(model);
        for (int i = 0; i < ProductColType.getNumCols(); i++) {
            setComparator(i, getComparatorForCol(i));
            this.setSortable(i, true);
        }
    }

    private Comparator<Product> getComparatorForCol(int colIndex) {
        switch (ProductColType.fromColIndex(colIndex)) {
            case NAME:
                return new ProductNameComparator();
            case DESCRIPTION:
                return new ProductDescriptionComparator();
            case PRICE:
                return new ProductPriceComparator();
            default:
                return null;
        }
    }

    class ProductNameComparator implements Comparator<Product> {
        @Override
        public int compare(Product p1, Product p2) {
            return p1.getName().compareTo(p2.getName());
        }
    }

    class ProductDescriptionComparator implements Comparator<Product> {
        @Override
        public int compare(Product p1, Product p2) {
            return p1.getDescription().compareTo(p2.getDescription());
        }
    }

    class ProductPriceComparator implements Comparator<Product> {
        @Override
        public int compare(Product p1, Product p2) {
            return p1.getPrice().compareTo(p2.getPrice());
        }
    }
}

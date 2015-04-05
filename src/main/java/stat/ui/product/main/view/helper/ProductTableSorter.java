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
 * @author Eray Tuncer             *
 *                                 *
 * ******************************* *
 */

import stat.domain.Product;

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

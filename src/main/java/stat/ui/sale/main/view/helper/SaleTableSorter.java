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

import stat.domain.Sale;

import java.util.Comparator;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class SaleTableSorter extends TableRowSorter {

    public SaleTableSorter(TableModel model) {
        super(model);
        for (int i = 0; i < SaleColType.getNumCols(); i++) {
            setComparator(i, getComparatorForCol(i));
            this.setSortable(i, true);
        }
    }

    private Comparator<Sale> getComparatorForCol(int colIndex) {
        if (SaleColType.fromColIndex(colIndex) == null) {
            return null;
        }

        switch (SaleColType.fromColIndex(colIndex)) {
            case NAME:
                return new SaleCustomerNameComparator();
            case DATE:
                return new SaleDateComparator();
            case PRICE:
                return new SalePriceComparator();
            default:
                return null;
        }
    }

    class SaleCustomerNameComparator implements Comparator<Sale> {
        @Override
        public int compare(Sale s1, Sale s2) {
            return s1.getCustomerName().compareTo(s2.getCustomerName());
        }
    }

    class SaleDateComparator implements Comparator<Sale> {
        @Override
        public int compare(Sale s1, Sale s2) {
            return s1.getDate().compareTo(s2.getDate());
        }
    }

    class SalePriceComparator implements Comparator<Sale> {
        @Override
        public int compare(Sale s1, Sale s2) {
            return s1.getTotalPrice().compareTo(s2.getTotalPrice());
        }
    }
}

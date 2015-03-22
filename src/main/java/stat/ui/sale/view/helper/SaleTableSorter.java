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

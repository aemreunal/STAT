package stat.ui.sale.main;

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
import stat.domain.Sale;
import stat.service.SaleService;
import stat.ui.Page;
import stat.ui.PageController;
import stat.ui.sale.add.SaleAddController;
import stat.ui.sale.main.view.helper.SaleFilterPanel;
import stat.ui.sale.main.view.SaleMainPage;
import stat.ui.sale.main.view.SaleDetailPage;
import stat.ui.sale.main.view.helper.SaleColType;

import java.math.BigDecimal;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
// Required to not run this class in a test environment
@ConditionalOnProperty(value = "java.awt.headless", havingValue = "false")
public class SaleController implements PageController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private SaleMainPage saleMainPage;

    @Autowired
    private SaleAddController saleAddController;

    private ArrayList<Integer> saleIDList = new ArrayList<>();

    @Override
    public void refreshPage() {
        showSales(saleService.getAllSales());
    }

    private void showSales(Set<Sale> salesToShow) {
        saleIDList.clear();
        Sale[] sales = salesToShow.toArray(new Sale[salesToShow.size()]);
        Object[][] saleTableObjects = new Object[sales.length][SaleColType.getColNameList().length];
        for (int i = 0; i < sales.length; i++) {
            Sale sale = sales[i];
            saleTableObjects[i] = new Object[] { sale.getCustomerName(), sale.getDate(), sale.getTotalPrice() };
            saleIDList.add(i, sale.getSaleId());
        }
        saleMainPage.setSalesList(saleTableObjects);
    }

    public void addSaleButtonClicked() {
        saleAddController.showSaleCreator();
    }

    public void removeSaleButtonClicked(int row) {
        if (row == -1) { // If no row has been chosen
            return;
        }
        int saleIdToRemove = saleIDList.remove(row);
        saleService.deleteSale(saleIdToRemove);
        refreshPage();
    }

    public void showSaleDetailsButtonClicked(int row) {
        if (row == -1) { // If no row has been chosen
            return;
        }
        Integer saleId = saleIDList.get(row);
        Sale sale = saleService.getSaleWithId(saleId);
        LinkedHashMap<Product, Integer> productsAndAmounts = saleService.getProductsOfSale(saleId);
        SaleDetailPage saleDetailPage = new SaleDetailPage(sale, productsAndAmounts);
        Page.showPopup(saleDetailPage);
    }

    public void applyFilterButtonClicked(SaleFilterPanel filterPanel) {
        String customerName = filterPanel.getCustomerName();
        Date fromDate = filterPanel.getFromDate();
        Date untilDate = filterPanel.getUntilDate();
        BigDecimal minPrice = filterPanel.getMinPrice();
        BigDecimal maxPrice = filterPanel.getMaxPrice();
        LinkedHashSet<Sale> sales = saleService.searchForSales(customerName, fromDate, untilDate, minPrice, maxPrice);
        showSales(sales);
    }
}


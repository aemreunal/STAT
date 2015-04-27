package stat.serviceTests;

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

import stat.StatTest;
import stat.domain.Product;
import stat.domain.Sale;
import stat.service.exception.ProductNameException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import static org.junit.Assert.*;

public class SalesTests extends StatTest {

    @Test
    @Rollback
    public void saleCreateTest() {
        Sale sale = saleService.createNewSale("test customer");
        Set<Sale> sales = saleService.getAllSales();
        assertTrue("Sale is not persisted!", sales.contains(sale));
    }

    @Test
    @Rollback
    public void saleFindByIdTest() {
        Sale createdSale = saleService.createNewSale("test customer");
        Sale retrievedSale = saleService.getSaleWithId(createdSale.getSaleId());
        assertEquals("Stored and retrieved sales are not equal!", createdSale, retrievedSale);
    }

    @Test
    @Rollback
    public void storeProductAmountInSaleTest() throws ProductNameException {
        Product product = productService.createNewProduct("test product", 1.0);
        Sale sale = saleService.createNewSale("test customer");
        int amount = getRandomAmount();
        sale = saleService.addProduct(sale, product.getProductId(), amount);

        LinkedHashMap<Product, Integer> productsOfSale = saleService.getProductsOfSale(sale.getSaleId());

        assertTrue("Product is not persisted in sale!", productsOfSale.containsKey(product));
        assertEquals("Amount of sale is not true!", amount, productsOfSale.get(product).intValue());
    }

    @Test
    @Rollback
    public void saleTotalPriceTest() throws ProductNameException {
        Sale sale = saleService.createNewSale("test customer");

        int amount1 = getRandomAmount();
        BigDecimal price1 = getRandomPrice();
        Product product1 = productService.createNewProduct("test product 1", price1);
        sale = saleService.addProduct(sale, product1.getProductId(), amount1);

        BigDecimal expectedTotalPrice1 = price1.multiply(BigDecimal.valueOf(amount1));
        assertEquals("The sale total price is not correctly calculated and stored!", expectedTotalPrice1, sale.getTotalPrice());

        int amount2 = getRandomAmount();
        BigDecimal price2 = getRandomPrice();
        Product product2 = productService.createNewProduct("test product 2", price2);
        sale = saleService.addProduct(sale, product2.getProductId(), amount2);

        BigDecimal expectedTotalPrice2 = expectedTotalPrice1.add(price2.multiply(BigDecimal.valueOf(amount2)));
        assertEquals("The sale total price is not correctly calculated and stored!", expectedTotalPrice2, sale.getTotalPrice());
    }

    @Test
    @Rollback
    public void saleDeleteTest() {
        Sale sale = saleService.createNewSale("test customer");
        Set<Sale> sales = saleService.getAllSales();
        assertTrue("Sale is not persisted!", sales.contains(sale));

        saleService.deleteSale(sale);
        sales = saleService.getAllSales();
        assertFalse("Sale is not deleted!", sales.contains(sale));
    }

    @Test
    @Rollback
    public void searchSalesBetweenDatesTest() throws ParseException {

        Date date1 = dateFormatter.parse("2011-02-01"); // 1 February 2011 - 00h00
        Date date2 = dateFormatter.parse("2011-06-01"); // 1 June 2011 - 00h00
        Date date3 = dateFormatter.parse("2012-01-01"); // 1 January 2012 - 00h00
        Date date4 = dateFormatter.parse("2013-01-01"); // 1 January 2013 - 00h00

        // Sale from 2011-02-01 - 00h00
        Sale sale1 = saleService.createNewSale("test customer", date1);

        // Sale from 2011-06-01 - 00h00
        Sale sale2 = saleService.createNewSale("test customer", date2);

        // Sale from 2012-01-01 - 00h00
        Sale sale3 = saleService.createNewSale("test customer", date3);

        // Sale from 2013-01-01 - 00h00
        Sale sale4 = saleService.createNewSale("test customer", date4);

        LinkedHashSet<Sale> sales = saleService.searchForSales(null, date1, date2, null, null);
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale1));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale2));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale3));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale4));
    }

    @Test
    @Rollback
    public void searchSalesFromDateTest() throws ParseException {

        Date date1 = dateFormatter.parse("2011-02-01"); // 1 February 2011 - 00h00
        Date date2 = dateFormatter.parse("2011-06-01"); // 1 June 2011 - 00h00
        Date date3 = dateFormatter.parse("2012-01-01"); // 1 January 2012 - 00h00
        Date date4 = dateFormatter.parse("2013-01-01"); // 1 January 2013 - 00h00

        // Sale from 2011-02-01 - 00h00
        Sale sale1 = saleService.createNewSale("test customer", date1);

        // Sale from 2011-06-01 - 00h00
        Sale sale2 = saleService.createNewSale("test customer", date2);

        // Sale from 2012-01-01 - 00h00
        Sale sale3 = saleService.createNewSale("test customer", date3);

        // Sale from 2013-01-01 - 00h00
        Sale sale4 = saleService.createNewSale("test customer", date4);

        LinkedHashSet<Sale> sales = saleService.searchForSales(null, date2, null, null, null);
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale1));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale2));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale3));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale4));
    }

    @Test
    @Rollback
    public void searchSalesUntilDateTest() throws ParseException {

        Date date1 = dateFormatter.parse("2011-02-01"); // 1 February 2011 - 00h00
        Date date2 = dateFormatter.parse("2011-06-01"); // 1 June 2011 - 00h00
        Date date3 = dateFormatter.parse("2012-01-01"); // 1 January 2012 - 00h00
        Date date4 = dateFormatter.parse("2013-01-01"); // 1 January 2013 - 00h00

        // Sale from 2011-02-01 - 00h00
        Sale sale1 = saleService.createNewSale("test customer", date1);

        // Sale from 2011-06-01 - 00h00
        Sale sale2 = saleService.createNewSale("test customer", date2);

        // Sale from 2012-01-01 - 00h00
        Sale sale3 = saleService.createNewSale("test customer", date3);

        // Sale from 2013-01-01 - 00h00
        Sale sale4 = saleService.createNewSale("test customer", date4);

        LinkedHashSet<Sale> sales = saleService.searchForSales(null, null, date3, null, null);
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale1));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale2));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale3));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale4));
    }

    @Test
    @Rollback
    public void searchNonExistentSalesWithDatesTest() throws ParseException {

        Date date1 = dateFormatter.parse("2011-02-01"); // 1 February 2011 - 00h00
        Date date2 = dateFormatter.parse("2011-06-01"); // 1 June 2011 - 00h00
        Date date3 = dateFormatter.parse("2012-01-01"); // 1 January 2012 - 00h00
        Date date4 = dateFormatter.parse("2013-01-01"); // 1 January 2013 - 00h00
        Date date5 = dateFormatter.parse("2009-01-01"); // 1 January 2009 - 00h00
        Date date6 = dateFormatter.parse("2009-02-01"); // 1 February 2009 - 00h00

        // Sale from 2011-02-01 - 00h00
        Sale sale1 = saleService.createNewSale("test customer", date1);

        // Sale from 2011-06-01 - 00h00
        Sale sale2 = saleService.createNewSale("test customer", date2);

        // Sale from 2012-01-01 - 00h00
        Sale sale3 = saleService.createNewSale("test customer", date3);

        // Sale from 2013-01-01 - 00h00
        Sale sale4 = saleService.createNewSale("test customer", date4);

        LinkedHashSet<Sale> sales = saleService.searchForSales(null, date5, date6, null, null);
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale1));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale2));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale3));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale4));
    }

    @Test
    @Rollback
    public void searchSalesByMinPriceTest() throws ParseException, ProductNameException {
        Sale sale1 = saleService.createNewSale("sale 1");
        Sale sale2 = saleService.createNewSale("sale 2");
        Sale sale3 = saleService.createNewSale("sale 3");
        Sale sale4 = saleService.createNewSale("sale 4");

        BigDecimal price = BigDecimal.valueOf(1.0);
        Product product = productService.createNewProduct("test product 1", price);

        sale1 = saleService.addProduct(sale1, product.getProductId(), 1);
        sale2 = saleService.addProduct(sale2, product.getProductId(), 2);
        sale3 = saleService.addProduct(sale3, product.getProductId(), 3);
        sale4 = saleService.addProduct(sale4, product.getProductId(), 4);

        LinkedHashSet<Sale> sales = saleService.searchForSales(null, null, null, price.multiply(BigDecimal.valueOf(2)), null);
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale1));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale2));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale3));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale4));
    }

    @Test
    @Rollback
    public void searchSalesByMaxPriceTest() throws ParseException, ProductNameException {
        Sale sale1 = saleService.createNewSale("sale 1");
        Sale sale2 = saleService.createNewSale("sale 2");
        Sale sale3 = saleService.createNewSale("sale 3");
        Sale sale4 = saleService.createNewSale("sale 4");

        BigDecimal price = BigDecimal.valueOf(1.0);
        Product product = productService.createNewProduct("test product 1", price);

        sale1 = saleService.addProduct(sale1, product.getProductId(), 1);
        sale2 = saleService.addProduct(sale2, product.getProductId(), 2);
        sale3 = saleService.addProduct(sale3, product.getProductId(), 3);
        sale4 = saleService.addProduct(sale4, product.getProductId(), 4);

        LinkedHashSet<Sale> sales = saleService.searchForSales(null, null, null, null, price.multiply(BigDecimal.valueOf(2)));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale1));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale2));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale3));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale4));
    }

    @Test
    @Rollback
    public void searchSalesByPriceIntervalTest() throws ParseException, ProductNameException {
        Sale sale1 = saleService.createNewSale("sale 1");
        Sale sale2 = saleService.createNewSale("sale 2");
        Sale sale3 = saleService.createNewSale("sale 3");
        Sale sale4 = saleService.createNewSale("sale 4");

        BigDecimal price = BigDecimal.valueOf(1.0);
        Product product = productService.createNewProduct("test product 1", price);

        sale1 = saleService.addProduct(sale1, product.getProductId(), 1);
        sale2 = saleService.addProduct(sale2, product.getProductId(), 2);
        sale3 = saleService.addProduct(sale3, product.getProductId(), 3);
        sale4 = saleService.addProduct(sale4, product.getProductId(), 4);

        LinkedHashSet<Sale> sales = saleService.searchForSales(null, null, null, price.multiply(BigDecimal.valueOf(2)), price.multiply(BigDecimal.valueOf(3)));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale1));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale2));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale3));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale4));
    }

    @Test
    @Rollback
    public void searchSalesByCustNamesTest() throws ParseException {

        Date date1 = dateFormatter.parse("2011-02-01"); // 1 February 2011 - 00h00
        Date date2 = dateFormatter.parse("2011-06-01"); // 1 June 2011 - 00h00
        Date date3 = dateFormatter.parse("2012-01-01"); // 1 January 2012 - 00h00
        Date date4 = dateFormatter.parse("2013-01-01"); // 1 January 2013 - 00h00

        // Sale from 2011-02-01 - 00h00
        Sale sale1 = saleService.createNewSale("test customer 1", date1);

        // Sale from 2011-06-01 - 00h00
        Sale sale2 = saleService.createNewSale("test customer 2", date2);

        // Sale from 2012-01-01 - 00h00
        Sale sale3 = saleService.createNewSale("3_test", date3);

        // Sale from 2013-01-01 - 00h00
        Sale sale4 = saleService.createNewSale("test CUstomer 3", date4);

        LinkedHashSet<Sale> sales = saleService.searchForSales("test", null, null, null, null);
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale1));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale2));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale3));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale4));
    }

    @Test
    @Rollback
    public void searchSalesByCustNameMixedCaseTest() throws ParseException {

        Date date1 = dateFormatter.parse("2011-02-01"); // 1 February 2011 - 00h00
        Date date2 = dateFormatter.parse("2011-06-01"); // 1 June 2011 - 00h00
        Date date3 = dateFormatter.parse("2012-01-01"); // 1 January 2012 - 00h00
        Date date4 = dateFormatter.parse("2013-01-01"); // 1 January 2013 - 00h00

        // Sale from 2011-02-01 - 00h00
        Sale sale1 = saleService.createNewSale("test customer 1", date1);

        // Sale from 2011-06-01 - 00h00
        Sale sale2 = saleService.createNewSale("test customer 2", date2);

        // Sale from 2012-01-01 - 00h00
        Sale sale3 = saleService.createNewSale("3_test", date3);

        // Sale from 2013-01-01 - 00h00
        Sale sale4 = saleService.createNewSale("test CUstomer 3", date4);

        LinkedHashSet<Sale> sales = saleService.searchForSales("custOMER", null, null, null, null);
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale1));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale2));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale3));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale4));
    }

    @Test
    @Rollback
    public void searchSalesByCustNameAndDateTest1() throws ParseException {

        Date date1 = dateFormatter.parse("2011-02-01"); // 1 February 2011 - 00h00
        Date date2 = dateFormatter.parse("2011-06-01"); // 1 June 2011 - 00h00
        Date date3 = dateFormatter.parse("2012-01-01"); // 1 January 2012 - 00h00
        Date date4 = dateFormatter.parse("2013-01-01"); // 1 January 2013 - 00h00

        // Sale from 2011-02-01 - 00h00
        Sale sale1 = saleService.createNewSale("test customer 1", date1);

        // Sale from 2011-06-01 - 00h00
        Sale sale2 = saleService.createNewSale("test customer 2", date2);

        // Sale from 2012-01-01 - 00h00
        Sale sale3 = saleService.createNewSale("3_test", date3);

        // Sale from 2013-01-01 - 00h00
        Sale sale4 = saleService.createNewSale("test CUstomer 3", date4);

        LinkedHashSet<Sale> sales = saleService.searchForSales("test", date1, date2, null, null);
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale1));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale2));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale3));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale4));
    }

    @Test
    @Rollback
    public void searchSalesByCustNameAndDateTest2() throws ParseException {

        Date date1 = dateFormatter.parse("2011-02-01"); // 1 February 2011 - 00h00
        Date date2 = dateFormatter.parse("2011-06-01"); // 1 June 2011 - 00h00
        Date date3 = dateFormatter.parse("2012-01-01"); // 1 January 2012 - 00h00
        Date date4 = dateFormatter.parse("2013-01-01"); // 1 January 2013 - 00h00

        // Sale from 2011-02-01 - 00h00
        Sale sale1 = saleService.createNewSale("test customer 1", date1);

        // Sale from 2011-06-01 - 00h00
        Sale sale2 = saleService.createNewSale("test customer 2", date2);

        // Sale from 2012-01-01 - 00h00
        Sale sale3 = saleService.createNewSale("3_test", date3);

        // Sale from 2013-01-01 - 00h00
        Sale sale4 = saleService.createNewSale("test CUstomer 3", date4);

        LinkedHashSet<Sale> sales = saleService.searchForSales("3", date1, date2, null, null);
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale1));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale2));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale3));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale4));
    }

    @Test
    @Rollback
    public void getCustomerNameTest() {
        String customerName1 = "test customer 1";
        String customerName2 = "test customer 2";
        saleService.createNewSale(customerName1);
        saleService.createNewSale(customerName2);
        saleService.createNewSale(customerName2);
        LinkedHashSet<String> customerNames = saleService.getCustomerNames();

        assertEquals("There are [erroneously] more than 2 customers!", 2, customerNames.size());
        assertTrue("One of the customers are not included in the names list!", customerNames.contains(customerName1));
        assertTrue("One of the customers are not included in the names list!", customerNames.contains(customerName2));
    }
}

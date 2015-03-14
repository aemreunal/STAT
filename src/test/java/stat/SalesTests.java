package stat;

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
import stat.domain.Sale;
import stat.exception.ProductNameException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import static junit.framework.Assert.*;

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
    public void saleFindTest() {
        Sale createdSale = saleService.createNewSale("test customer");
        Sale retrievedSale = saleService.getSaleWithId(createdSale.getSaleId());
        assertEquals("Stored and retrieved sales are not equal!", createdSale, retrievedSale);
    }

    @Test
    @Rollback
    public void amountTest() throws ProductNameException {
        Product product = productService.createNewProduct("test product", 1.0);
        Sale sale = saleService.createNewSale("test customer");
        int amount = 4;
        sale = saleService.addProduct(sale, product, amount);

        LinkedHashMap<Product, Integer> productsOfSale = saleService.getProductsOfSale(sale.getSaleId());

        assertTrue("Product is not persisted in sale!", productsOfSale.containsKey(product));
        assertEquals("Amount of sale is not true!", amount, productsOfSale.get(product).intValue());
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
    public void saleDateSearchTest() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = formatter.parse("2011-02-01"); // 1 February 2011 - 00h00
        Date date2 = formatter.parse("2011-06-01"); // 1 June 2011 - 00h00
        Date date3 = formatter.parse("2012-01-01"); // 1 January 2012 - 00h00
        Date date4 = formatter.parse("2013-01-01"); // 1 January 2013 - 00h00
        Date date5 = formatter.parse("2009-01-01"); // 1 January 2009 - 00h00
        Date date6 = formatter.parse("2009-02-01"); // 1 February 2009 - 00h00

        // Sale from 2011-02-01 - 00h00
        Sale sale1 = new Sale("test customer");
        sale1.setDate(date1);
        sale1 = saleRepo.save(sale1);

        // Sale from 2011-06-01 - 00h00
        Sale sale2 = new Sale("test customer");
        sale2.setDate(date2);
        sale2 = saleRepo.save(sale2);

        // Sale from 2012-01-01 - 00h00
        Sale sale3 = new Sale("test customer");
        sale3.setDate(date3);
        sale3 = saleRepo.save(sale3);

        // Sale from 2013-01-01 - 00h00
        Sale sale4 = new Sale("test customer");
        sale4.setDate(date4);
        sale4 = saleRepo.save(sale4);

        LinkedHashSet<Sale> sales = saleService.getSalesBetween(date1, date2);
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale1));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale2));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale3));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale4));

        sales = saleService.getSalesBetween(date2, date3);
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale1));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale2));
        assertTrue("Search result does not contain sales from the searched interval!", sales.contains(sale3));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale4));

        sales = saleService.getSalesBetween(date5, date6);
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale1));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale2));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale3));
        assertFalse("Search result contains sales from outside the interval!", sales.contains(sale4));
    }
}

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

import java.util.LinkedHashMap;
import java.util.Set;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class SalesTests extends StatTest {

    @Test
    @Rollback
    public void saleCreateTest() {
        Sale sale = saleService.createNewSale();
        Set<Sale> sales = saleService.getAllSales();
        assertTrue("Sale is not persisted!", sales.contains(sale));
    }

    @Test
    @Rollback
    public void saleFindTest() {
        Sale createdSale = saleService.createNewSale();
        Sale retrievedSale = saleService.getSaleWithId(createdSale.getSaleId());
        assertEquals("Stored and retrieved sales are not equal!", createdSale, retrievedSale);
    }

    @Test
    @Rollback
    public void amountTest() {
        Product product = productService.createNewProduct();
        Sale sale = saleService.createNewSale();
        int amount = 4;
        sale = saleService.addProduct(sale, product, amount);

        LinkedHashMap<Product, Integer> productsOfSale = saleService.getProductsOfSale(sale.getSaleId());

        assertTrue("Product is not persisted in sale!", productsOfSale.containsKey(product));
        assertEquals("Amount of sale is not true!", amount, productsOfSale.get(product).intValue());
    }

    @Test
    @Rollback
    public void saleDeleteTest() {
        Sale sale = saleService.createNewSale();
        Set<Sale> sales = saleService.getAllSales();
        assertTrue("Sale is not persisted!", sales.contains(sale));

        saleService.deleteSale(sale);
        sales = saleService.getAllSales();
        assertFalse("Sale is not deleted!", sales.contains(sale));
    }

}

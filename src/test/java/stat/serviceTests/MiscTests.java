package stat.serviceTests;

import stat.StatTest;
import stat.domain.Product;
import stat.domain.Sale;
import stat.exception.ProductNameException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

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

public class MiscTests extends StatTest {

    @Before
    public void setUpTest() {
        System.out.println("<------TEST------>");
    }

    @Test
    @Rollback
    public void generalFunctionalityTest() throws ProductNameException {
        Product product1 = productService.createNewProduct("test product 1", 1.0);
        Product product2 = productService.createNewProduct("test product 2", 1.0);

        Sale sale1 = saleService.createNewSale("test customer");
        Sale sale2 = saleService.createNewSale("test customer");

        sale1 = saleService.addProduct(sale1, product1, 4);
        sale1 = saleService.addProduct(sale1, product2, 4);

        sale2 = saleService.addProduct(sale2, product1, 4);
        sale2 = saleService.addProduct(sale2, product2, 4);
    }
}

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

import java.util.Set;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;

public class ProductTests extends StatTest {

    @Test
    @Rollback
    public void productCreateTest() {
        Product product = productService.createNewProduct();
        Set<Product> products = productService.getAllProducts();
        assertTrue("Product is not persisted!", products.contains(product));
    }

    @Test
    @Rollback
    public void productFindTest() {
        Product createdProduct = productService.createNewProduct();
        Product retrievedProduct = productService.getProductWithId(createdProduct.getProductId());
        assertEquals("Stored and retrieved products are not equal!", createdProduct, retrievedProduct);
    }

    @Test
    @Rollback
    public void productSalesTest() {
        Product product = productService.createNewProduct();
        Sale sale1 = saleService.createNewSale();
        Sale sale2 = saleService.createNewSale();
        int amount = 1;
        sale1 = saleService.addProduct(sale1, product, amount);
        sale2 = saleService.addProduct(sale2, product, amount);

        // Updated product object, which reflects the changes made in
        // sales, must be obtained. Therefore, sales are obtained through
        // the Product service.
        Set<Sale> salesOfProduct = productService.getSalesOfProduct(product.getProductId());
        assertTrue("Product does not contain the sale!", salesOfProduct.contains(sale1));
        assertTrue("Product does not contain the sale!", salesOfProduct.contains(sale2));
    }

    @Test
    @Rollback
    public void productDeleteTest() {
        Product product = productService.createNewProduct();
        Set<Product> products = productService.getAllProducts();
        assertTrue("Product is not persisted!", products.contains(product));

        productService.deleteProduct(product);
        products = productService.getAllProducts();
        assertFalse("Product is not deleted!", products.contains(product));
    }

}

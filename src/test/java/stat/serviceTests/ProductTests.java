package stat.serviceTests;

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

import stat.StatTest;
import stat.domain.Product;
import stat.domain.Sale;
import stat.exception.ProductNameException;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import static junit.framework.Assert.*;

public class ProductTests extends StatTest {

    @Test
    @Rollback
    public void productCreateTest1() throws ProductNameException {
        Product product = productService.createNewProduct("test product", 1.0);
        Set<Product> products = productService.getAllProducts();
        assertTrue("Product is not persisted!", products.contains(product));
    }

    @Test(expected = ProductNameException.class)
    @Rollback
    public void productCreateTest2() throws ProductNameException {
        productService.createNewProduct("test product", 1.0);
        productService.createNewProduct("test product", 1.0);
    }

    @Test
    @Rollback
    public void productPriceTest1() throws ProductNameException {
        BigDecimal price = BigDecimal.valueOf(1.1);
        Product product1 = productService.createNewProduct("test product 1", price);
        assertTrue("Product price is not stored properly!", productService.getProductWithId(product1.getProductId()).getPrice().equals(price));
    }

    @Test
    @Rollback
    public void productPriceTest2() throws ProductNameException {
        BigDecimal price = BigDecimal.valueOf(1.1234);
        Product product1 = productService.createNewProduct("test product 1", price);
        assertTrue("Product price is not stored properly!", productService.getProductWithId(product1.getProductId()).getPrice().equals(price));
    }

    @Test
    @Rollback
    public void productFindTest() throws ProductNameException {
        Product createdProduct = productService.createNewProduct("test product", 1.0);
        Product retrievedProduct = productService.getProductWithId(createdProduct.getProductId());
        assertEquals("Stored and retrieved products are not equal!", createdProduct, retrievedProduct);
    }

    @Test
    @Rollback
    public void productSalesTest() throws ProductNameException {
        Product product = productService.createNewProduct("test product", 1.0);
        Sale sale1 = saleService.createNewSale("test customer");
        Sale sale2 = saleService.createNewSale("test customer");
        int amount = 1;
        sale1 = saleService.addProduct(sale1, product, amount);
        sale2 = saleService.addProduct(sale2, product, amount);

        // Updated product object, which reflects the changes made in
        // sales, must be obtained. Therefore, sales are obtained through
        // the Product service.
        LinkedHashSet<Sale> salesOfProduct = productService.getSalesOfProduct(product.getProductId());
        assertTrue("Product does not contain the sale!", salesOfProduct.contains(sale1));
        assertTrue("Product does not contain the sale!", salesOfProduct.contains(sale2));
    }

    @Test
    @Rollback
    public void productNameSearchTest1() throws ProductNameException {
        Product product1 = productService.createNewProduct("test product 1", 1.0);
        Product product2 = productService.createNewProduct("test 2", 1.0);
        Product product3 = productService.createNewProduct("test product 2", 1.0);

        Set<Product> products = productService.getProductsWithNameContaining("test");
        assertTrue("Search result does not contain the product!", products.contains(product1));
        assertTrue("Search result does not contain the product!", products.contains(product2));
        assertTrue("Search result does not contain the product!", products.contains(product3));
    }

    @Test
    @Rollback
    public void productNameSearchTest2() throws ProductNameException {
        Product product1 = productService.createNewProduct("test product 1", 1.0);
        Product product2 = productService.createNewProduct("test 2", 1.0);
        Product product3 = productService.createNewProduct("test product 2", 1.0);

        Set<Product> products = productService.getProductsWithNameContaining("product");
        assertTrue("Search result does not contain the searched product!", products.contains(product1));
        assertFalse("Search result contains a product not searched for!", products.contains(product2));
        assertTrue("Search result does not contain the searched product!", products.contains(product3));
    }

    @Test
    @Rollback
    public void productNameSearchTest3() throws ProductNameException {
        Product product1 = productService.createNewProduct("test product 1", 1.0);
        Product product2 = productService.createNewProduct("test 2", 1.0);
        Product product3 = productService.createNewProduct("test product 2", 1.0);

        Set<Product> products = productService.getProductsWithNameContaining("2");
        assertFalse("Search result contains a product not searched for!", products.contains(product1));
        assertTrue("Search result does not contain the searched product!", products.contains(product2));
        assertTrue("Search result does not contain the searched product!", products.contains(product3));
    }

    @Test
    @Rollback
    public void productNameSearchTest4() throws ProductNameException {
        Product product1 = productService.createNewProduct("test product 1", 1.0);
        Product product2 = productService.createNewProduct("test 2", 1.0);
        Product product3 = productService.createNewProduct("test product 2", 1.0);

        Set<Product> products = productService.getProductsWithNameContaining("ST");
        assertTrue("Search result does not contain the product!", products.contains(product1));
        assertTrue("Search result does not contain the product!", products.contains(product2));
        assertTrue("Search result does not contain the product!", products.contains(product3));
    }

    @Test
    @Rollback
    public void productDescriptionSearchTest1() throws ProductNameException {
        Product product1 = productService.createNewProduct("test product 1", "test product 1", 1.0);
        Product product2 = productService.createNewProduct("test product 2", "test 2", 1.0);
        Product product3 = productService.createNewProduct("test product 3", "test product 2", 1.0);

        Set<Product> products = productService.getProductsWithDescriptionContaining("2");
        assertFalse("Search result contains a product not searched for!", products.contains(product1));
        assertTrue("Search result does not contain the searched product!", products.contains(product2));
        assertTrue("Search result does not contain the searched product!", products.contains(product3));
    }

    @Test
    @Rollback
    public void productDescriptionSearchTest2() throws ProductNameException {
        Product product1 = productService.createNewProduct("test product1", "test product 1", 1.0);
        Product product2 = productService.createNewProduct("test product2", "test 2", 1.0);
        Product product3 = productService.createNewProduct("test product3", "test product 2", 1.0);

        Set<Product> products = productService.getProductsWithDescriptionContaining("ST");
        assertTrue("Search result does not contain the product!", products.contains(product1));
        assertTrue("Search result does not contain the product!", products.contains(product2));
        assertTrue("Search result does not contain the product!", products.contains(product3));
    }

    @Test
    @Rollback
    public void productGeneralSearchTest1() throws ProductNameException {
        Product product1 = productService.createNewProduct("test product 1", "test product 1", 1.0);
        Product product2 = productService.createNewProduct("test 2", "test 2", 1.5);
        Product product3 = productService.createNewProduct("test product 2", "test product 2", 2.0);

        Set<Product> products = productService.searchForProducts("teST", null, null, null);
        assertTrue("Search result does not contain the product!", products.contains(product1));
        assertTrue("Search result does not contain the product!", products.contains(product2));
        assertTrue("Search result does not contain the product!", products.contains(product3));
    }

    @Test
    @Rollback
    public void productGeneralSearchTest2() throws ProductNameException {
        Product product1 = productService.createNewProduct("test product 1", "test product 1", 1.0);
        Product product2 = productService.createNewProduct("test 2", "test 2", 1.5);
        Product product3 = productService.createNewProduct("test product 2", "test product 2", 2.0);

        Set<Product> products = productService.searchForProducts("2", "pro", null, null);
        assertFalse("Search result contains a product not searched for!", products.contains(product1));
        assertFalse("Search result contains a product not searched for!", products.contains(product2));
        assertTrue("Search result does not contain the product!", products.contains(product3));
    }

    @Test
    @Rollback
    public void productGeneralPriceSearchTest1() throws ProductNameException {
        Product product1 = productService.createNewProduct("test product 1", "test product 1", 1.0);
        Product product2 = productService.createNewProduct("test 2", "test 2", 1.5);
        Product product3 = productService.createNewProduct("test product 2", "test product 2", 2.0);

        Set<Product> products = productService.searchForProducts(null, null, 1.2, 1.5);
        assertFalse("Search result contains a product not searched for!", products.contains(product1));
        assertTrue("Search result does not contain the product!", products.contains(product2));
        assertFalse("Search result contains a product not searched for!", products.contains(product3));
    }

    @Test
    @Rollback
    public void productGeneralPriceSearchTest2() throws ProductNameException {
        Product product1 = productService.createNewProduct("test product 1", "test product 1", 1.0);
        Product product2 = productService.createNewProduct("test 2", "test 2", 1.5);
        Product product3 = productService.createNewProduct("test product 2", "test product 2", 2.0);

        Set<Product> products = productService.searchForProducts(null, "PRO", 1.2, null);
        assertFalse("Search result contains a product not searched for!", products.contains(product1));
        assertFalse("Search result contains a product not searched for!", products.contains(product2));
        assertTrue("Search result does not contain the product!", products.contains(product3));
    }

    @Test
    @Rollback
    public void productDeleteTest() throws ProductNameException {
        Product product = productService.createNewProduct("test product", 1.0);
        Set<Product> products = productService.getAllProducts();
        assertTrue("Product is not persisted!", products.contains(product));

        productService.deleteProduct(product);
        products = productService.getAllProducts();
        assertFalse("Product is not deleted!", products.contains(product));
    }

}

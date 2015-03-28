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
import stat.service.exception.ProductNameException;
import stat.service.exception.ProductNotFoundException;
import stat.service.exception.SoldProductDeletionException;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import static junit.framework.Assert.*;

public class ProductTests extends StatTest {

    @Test
    @Rollback
    public void productCreateTest() throws ProductNameException {
        Product product = productService.createNewProduct("test product", 1.0);
        Set<Product> products = productService.getAllProducts();
        assertTrue("Product is not persisted!", products.contains(product));
    }

    @Test
    @Rollback
    public void deleteUnsoldProductTest() throws ProductNameException, SoldProductDeletionException {
        Product product = productService.createNewProduct("test product", 1.0);
        Set<Product> products = productService.getAllProducts();
        assertTrue("Product is not persisted!", products.contains(product));

        productService.deleteProduct(product.getProductId());
        products = productService.getAllProducts();
        assertFalse("Product is not deleted!", products.contains(product));
    }

    @Test(expected = SoldProductDeletionException.class)
    @Rollback
    public void deleteSoldProductTest() throws ProductNameException, SoldProductDeletionException {
        Product product = productService.createNewProduct("test product", 1.0);
        Integer productId = product.getProductId();
        Set<Product> products = productService.getAllProducts();
        assertTrue("Product is not persisted!", products.contains(product));

        Sale sale = saleService.createNewSale("test customer");
        saleService.addProduct(sale, productId, 1);
        LinkedHashSet<Sale> salesOfProduct = productService.getSalesOfProduct(productId);
        assertTrue("Product is not added to sale!", salesOfProduct.contains(sale));

        productService.deleteProduct(productId);
    }

    @Test(expected = ProductNameException.class)
    @Rollback
    public void createTwoProductWithTheSameNameTest() throws ProductNameException {
        productService.createNewProduct("test product", 1.0);
        productService.createNewProduct("test product", 1.0);
    }

    @Test
    @Rollback
    public void productPriceDecimalSaveTest1() throws ProductNameException {
        BigDecimal price = BigDecimal.valueOf(1.1);
        Product product1 = productService.createNewProduct("test product 1", price);
        // The comparison is tested against 0 as the result of compareTo() is equal to 0 when the two values are equal.
        assertEquals("Product price is not stored properly!", 0, productService.getProductWithId(product1.getProductId()).getPrice().compareTo(price));
    }

    @Test
    @Rollback
    public void productPriceDecimalSaveTest2() throws ProductNameException {
        BigDecimal price = BigDecimal.valueOf(1.1234);
        Product product1 = productService.createNewProduct("test product 1", price);
        // The comparison is tested against 0 as the result of compareTo() is equal to 0 when the two values are equal.
        assertEquals("Product price is not stored properly!", 0, productService.getProductWithId(product1.getProductId()).getPrice().compareTo(price));
    }

    @Test
    @Rollback
    public void productPriceDecimalRoundSaveTest() throws ProductNameException {
        BigDecimal price = BigDecimal.valueOf(1.1234567);
        BigDecimal roundedPrice = BigDecimal.valueOf(1.1235); // The rounding is correct for 4 decimal precision
        Product product1 = productService.createNewProduct("test product 1", price);
        // The comparison is tested against 0 as the result of compareTo() is equal to 0 when the two values are equal.
        assertEquals("Product price is not stored properly!", 0, productService.getProductWithId(product1.getProductId()).getPrice().compareTo(roundedPrice));
    }

    @Test
    @Rollback
    public void findProductByIdTest() throws ProductNameException {
        Product createdProduct = productService.createNewProduct("test product", 1.0);
        Product retrievedProduct = productService.getProductWithId(createdProduct.getProductId());
        assertEquals("Stored and retrieved products are not equal!", createdProduct, retrievedProduct);
    }

    @Test
    @Rollback
    public void productSalesRetrievalTest() throws ProductNameException {
        Product product = productService.createNewProduct("test product", 1.0);
        Sale sale1 = saleService.createNewSale("test customer");
        Sale sale2 = saleService.createNewSale("test customer");
        int amount = 1;
        sale1 = saleService.addProduct(sale1, product.getProductId(), amount);
        sale2 = saleService.addProduct(sale2, product.getProductId(), amount);

        // Updated product object, which reflects the changes made in
        // sales, must be obtained. Therefore, sales are obtained through
        // the Product service.
        LinkedHashSet<Sale> salesOfProduct = productService.getSalesOfProduct(product.getProductId());
        assertTrue("Product does not contain the sale!", salesOfProduct.contains(sale1));
        assertTrue("Product does not contain the sale!", salesOfProduct.contains(sale2));
    }

    @Test
    @Rollback
    public void getProductByNameTest() throws ProductNameException, ProductNotFoundException {
        String productName = "test product";
        Product newProduct = productService.createNewProduct(productName, 1.0);
        Product product = productService.getProductWithName(productName);
        assertEquals("The retrieved product is not the same as the stored one!", newProduct, product);
    }

    @Test(expected = ProductNotFoundException.class)
    @Rollback
    public void getProductByNameMultipleMatchTest() throws ProductNameException, ProductNotFoundException {
        String productName = "test product";
        productService.createNewProduct(productName + "1", 1.0);
        productService.createNewProduct(productName + "2", 1.0);
        productService.getProductWithName(productName);
    }

    @Test(expected = ProductNotFoundException.class)
    @Rollback
    public void getNonExistantProductByNameTest() throws ProductNameException, ProductNotFoundException {
        String productName = "test product";
        productService.getProductWithName(productName);
    }

    @Test
    @Rollback
    public void searchProductByNameTest2() throws ProductNameException {
        Product product1 = productService.createNewProduct("test product 1", 1.0);
        Product product2 = productService.createNewProduct("test 2", 1.0);
        Product product3 = productService.createNewProduct("test product 2", 1.0);

        Set<Product> products = productService.searchForProducts("product", null, null, null);
        assertTrue("Search result does not contain the searched product!", products.contains(product1));
        assertFalse("Search result contains a product not searched for!", products.contains(product2));
        assertTrue("Search result does not contain the searched product!", products.contains(product3));
    }

    @Test
    @Rollback
    public void searchProductByNameTest3() throws ProductNameException {
        Product product1 = productService.createNewProduct("test product 1", 1.0);
        Product product2 = productService.createNewProduct("test 2", 1.0);
        Product product3 = productService.createNewProduct("test product 2", 1.0);

        Set<Product> products = productService.searchForProducts("2", null, null, null);
        assertFalse("Search result contains a product not searched for!", products.contains(product1));
        assertTrue("Search result does not contain the searched product!", products.contains(product2));
        assertTrue("Search result does not contain the searched product!", products.contains(product3));
    }

    @Test
    @Rollback
    public void searchProductByNameMixedCaseTest() throws ProductNameException {
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
    public void searchProductByNameTest1() throws ProductNameException {
        Product product1 = productService.createNewProduct("test product 1", "test product 1", 1.0);
        Product product2 = productService.createNewProduct("test 2", "test 2", 1.5);
        Product product3 = productService.createNewProduct("test product 2", "test product 2", 2.0);

        Set<Product> products = productService.searchForProducts("test", null, null, null);
        assertTrue("Search result does not contain the product!", products.contains(product1));
        assertTrue("Search result does not contain the product!", products.contains(product2));
        assertTrue("Search result does not contain the product!", products.contains(product3));
    }

    @Test
    @Rollback
    public void searchProductByNameAndDescTest() throws ProductNameException {
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
    public void searchProductByPriceRangeTest() throws ProductNameException {
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
    public void searchProductByDescAndCeilingPriceTest() throws ProductNameException {
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
    public void searchProductByDescTest() throws ProductNameException {
        Product product1 = productService.createNewProduct("test product 1", "test product 1", 1.0);
        Product product2 = productService.createNewProduct("test product 2", "test 2", 1.0);
        Product product3 = productService.createNewProduct("test product 3", "test product 2", 1.0);

        Set<Product> products = productService.searchForProducts(null, "2", null, null);
        assertFalse("Search result contains a product not searched for!", products.contains(product1));
        assertTrue("Search result does not contain the searched product!", products.contains(product2));
        assertTrue("Search result does not contain the searched product!", products.contains(product3));
    }

    @Test
    @Rollback
    public void searchProductByDescMixedCaseTest() throws ProductNameException {
        Product product1 = productService.createNewProduct("test product1", "test product 1", 1.0);
        Product product2 = productService.createNewProduct("test product2", "test 2", 1.0);
        Product product3 = productService.createNewProduct("test product3", "test product 2", 1.0);

        Set<Product> products = productService.searchForProducts(null, "sT", null, null);
        assertTrue("Search result does not contain the product!", products.contains(product1));
        assertTrue("Search result does not contain the product!", products.contains(product2));
        assertTrue("Search result does not contain the product!", products.contains(product3));
    }

}

package stat;

import junit.framework.TestCase;
import stat.domain.Product;
import stat.service.ProductService;

import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class TestTest {
    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private ProductService productService;

    private Product product;

    @Before
    public void setUpTest() {
        product = productService.createNewProduct();
    }

    @Test
    public void test1() {
        Set<Product> products = productService.getAllProducts();
        TestCase.assertTrue(products.contains(product));
    }
}

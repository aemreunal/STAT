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
import stat.service.ProductService;
import stat.service.SaleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Stat implements CommandLineRunner {

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private SaleService saleService;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("------TEST-----");

        Product product1 = productService.createNewProduct();
        Product product2 = productService.createNewProduct();

        Sale sale1 = saleService.createNewSale();
        Sale sale2 = saleService.createNewSale();

        sale1 = saleService.addProduct(sale1, product1, 4);
        sale1 = saleService.addProduct(sale1, product2, 4);

        sale2 = saleService.addProduct(sale2, product1, 4);
        sale2 = saleService.addProduct(sale2, product2, 4);

        System.out.println("------TEST-----");
    }
}

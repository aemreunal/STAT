package stat.service;

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
import stat.repository.SaleRepo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class SaleService {
    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private SaleRepo saleRepo;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private ProductService productService;

    public Sale createNewSale() {
        Sale sale = new Sale();
        sale = this.save(sale);
        return sale;
    }

    private Sale save(Sale sale) {
        return saleRepo.save(sale);
    }

    @Transactional(readOnly = true)
    public Set<Sale> getAllSales() {
        return saleRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Sale getSaleWithId(Integer saleId) {
        return saleRepo.findOne(saleId);
    }

    public Sale addProduct(Integer saleId, Product product, int amount) {
        Sale sale = this.getSaleWithId(saleId);
        return this.addProduct(sale, product, amount);
    }

    public Sale addProduct(Sale sale, Product product, int amount) {
        sale.addProductToSale(product, amount);
        productService.addSale(product, sale);
        return this.save(sale);
    }

    @Transactional(readOnly = true)
    public LinkedHashMap<Product, Integer> getProductsOfSale(Integer saleId) {
        Sale sale = this.getSaleWithId(saleId);
        Set<Product> products = sale.getProducts();
        Map<Integer, Integer> amounts = sale.getAmounts();

        LinkedHashMap<Product, Integer> productsAndAmounts = new LinkedHashMap<Product, Integer>();
        for (Product product : products) {
            Integer amountOfProduct = amounts.get(product.getProductId());
            productsAndAmounts.put(product, amountOfProduct);
        }
        return productsAndAmounts;
    }

    public void deleteSale(Sale sale) {
        saleRepo.delete(sale);
    }

    public void deleteSale(Integer saleId) {
        saleRepo.delete(saleId);
    }
}

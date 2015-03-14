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
import stat.repository.ProductRepo;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProductService {
    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private ProductRepo productRepo;


    public Product createNewProduct(String name) {
        return this.createNewProduct(name, "");
    }

    public Product createNewProduct(String name, String description) {
        Product product = new Product(name, description);
        product = this.save(product);
        return product;
    }

    private Product save(Product product) {
        return productRepo.save(product);
    }

    @Transactional(readOnly = true)
    public Set<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Product getProductWithId(Integer productId) {
        return productRepo.findOne(productId);
    }

    @Transactional(readOnly = true)
    public LinkedHashSet<Sale> getSalesOfProduct(Integer productId) {
        Product product = this.getProductWithId(productId);
        return product.getSales().stream().collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Product addSale(Product product, Sale sale) {
        product.getSales().add(sale);
        return this.save(product);
    }

    public void deleteProduct(Integer productId) {
        productRepo.delete(productId);
    }
    public void deleteProduct(Product product) {
        productRepo.delete(product);
    }
}

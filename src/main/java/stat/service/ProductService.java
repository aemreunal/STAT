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
import stat.exception.ProductNameException;
import stat.repository.ProductRepo;

import java.math.BigDecimal;
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


    public Product createNewProduct(String name, Double price) throws ProductNameException {
        return this.createNewProduct(name, "", BigDecimal.valueOf(price));
    }

    public Product createNewProduct(String name, BigDecimal price) throws ProductNameException {
        return this.createNewProduct(name, "", price);
    }

    public Product createNewProduct(String name, String description, Double price) throws ProductNameException {
        return this.createNewProduct(name, description, BigDecimal.valueOf(price));
    }

    public Product createNewProduct(String name, String description, BigDecimal price) throws ProductNameException {
        if (nameExists(name)) {
            throw new ProductNameException(name);
        }
        Product product = new Product(name, description, price);
        product = this.save(product);
        return product;
    }

    private boolean nameExists(String name) {
        Set<Product> products = productRepo.findByNameLike(name);
        return products.size() != 0;
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
    public Set<Product> getProductsWithNameContaining(String productName) {
        return productRepo.findByNameContaining(productName);
    }

    @Transactional(readOnly = true)
    public Set<Product> getProductsWithDescriptionContaining(String productDescription) {
        return productRepo.findByDescriptionContaining(productDescription);
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

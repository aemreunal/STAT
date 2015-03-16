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
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProductService {
    @Autowired
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

    /**
     * Searches products with the given criteria. If a {@code null} value is provided for
     * any criteria parameter, that criteria won't be included in the search. For
     * example:
     * <pre>
     * searchForProducts(null, null, 1.2, null);</pre>
     * will return all products with prices larger than (or equal to) the given {@link
     * java.lang.Double double} price value. Another example would be that:
     * <pre>
     * searchForProducts('app', null, null, 5.8);</pre>
     * will return all products with names including the string 'app' (e.g. 'Apple',
     * 'Pineapple') and with prices smaller than (or equal to) the given {@link
     * java.lang.Double double} price value.
     *
     * @param name
     *         The product name to limit the search to. Could be a substring of the name.
     *         Case-insensitive. If a {@code null} value is provided, the search won't be
     *         restricted to a specific product name.
     * @param description
     *         The product description to limit the search to. Could be a substring of the
     *         description. Case-insensitive. If a {@code null} value is provided, the
     *         search won't be restricted to a specific product description.
     * @param minPrice
     *         Limits the price of the product search to be larger than (or equal to) this
     *         specified value. The specified price is also included (so a product at the
     *         exact same price will also be returned). If a {@code null} value is
     *         provided, the search won't be restricted to a specific floor price.
     * @param maxPrice
     *         Limits the price of the product search to be smaller than (or equal to)
     *         this specified value. The specified price is also included (so a product at
     *         the exact same price will also be returned). If a {@code null} value is
     *         provided, the search won't be restricted to a specific ceiling price.
     *
     * @return The {@link java.util.LinkedHashSet Set} of {@link stat.domain.Product
     * products} which match the given criteria.
     */
    @Transactional(readOnly = true)
    public LinkedHashSet<Product> searchForProducts(String name, String description, Double minPrice, Double maxPrice) {
        List searchResult = productRepo.findAll(productWithSpecification(name, description, minPrice, maxPrice));

        LinkedHashSet<Product> products = new LinkedHashSet<Product>();
        for (Object productObject : searchResult) {
            products.add(((Product) productObject));
        }
        return products;
    }

    public void deleteProduct(Integer productId) {
        productRepo.delete(productId);
    }

    public void deleteProduct(Product product) {
        productRepo.delete(product);
    }

    private Specification<Product> productWithSpecification(String name, String description, Double minPrice, Double maxPrice) {
        return (root, query, builder) -> {
            ArrayList<Predicate> predicates = new ArrayList<Predicate>();

            if (name != null && !name.equals("")) {
                predicates.add(builder.like(builder.lower(root.get("name").as(String.class)), "%" + name.toLowerCase() + "%"));
            }

            if (description != null && !description.equals("")) {
                predicates.add(builder.like(builder.lower(root.get("description").as(String.class)), "%" + description.toLowerCase() + "%"));
            }

            if (minPrice != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("price").as(BigDecimal.class), BigDecimal.valueOf(minPrice)));
            }

            if (maxPrice != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("price").as(BigDecimal.class), BigDecimal.valueOf(maxPrice)));
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}

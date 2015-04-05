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
import stat.service.exception.ProductNameException;
import stat.service.exception.ProductNotFoundException;
import stat.service.exception.SoldProductDeletionException;
import stat.service.repository.ProductRepo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
        Sort sortByIdAsc = new Sort(Sort.Direction.ASC, "productId");
        return productRepo.findAll(sortByIdAsc);
    }

    @Transactional(readOnly = true)
    public Product getProductWithId(Integer productId) {
        return productRepo.findOne(productId);
    }

    @Transactional(readOnly = true)
    public Product getProductWithName(String productName) throws ProductNotFoundException {
        Set<Product> products = productRepo.findByNameLike(productName);
        if (products.size() != 1) {
            throw new ProductNotFoundException(productName);
        }
        return ((Product) products.toArray()[0]);
    }

    @Transactional(readOnly = true)
    public Integer getIdOfProductWithName(String productName) throws ProductNotFoundException {
        return this.getProductWithName(productName).getProductId();
    }

    @Transactional(readOnly = true)
    public LinkedHashSet<Sale> getSalesOfProduct(Integer productId) {
        Product product = this.getProductWithId(productId);
        return product.getSales().stream().collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Transactional(readOnly = true)
    public int getAmountOfProductSoldTotal(Integer productId) {
        LinkedHashSet<Sale> sales = this.getSalesOfProduct(productId);
        int amountSold = 0;
        for (Sale sale : sales) {
            amountSold += sale.getAmounts().get(productId);
        }
        return amountSold;
    }

    @Transactional(readOnly = true)
    public BigDecimal getPriceOfProductSoldTotal(Integer productId) {
        int amountSold = this.getAmountOfProductSoldTotal(productId);
        BigDecimal price = this.getProductWithId(productId).getPrice();
        return price.multiply(BigDecimal.valueOf(amountSold));
    }

    /**
     * WARNING: DO NOT USE THIS METHOD.
     * <p>
     * This method is public only be used by the {@link stat.service.SaleService#addProduct(stat.domain.Sale,
     * Integer, int) addProduct} method of the {@link stat.service.SaleService
     * SaleService}. It is otherwise an internal and private method.
     * <p>
     * To add products to a sale, please use the {@link stat.service.SaleService#addProduct(stat.domain.Sale,
     * Integer, int) addProduct} method of the {@link stat.service.SaleService
     * SaleService}.
     *
     * @param product
     *         The product to add to the sale.
     * @param sale
     *         The sale to add the product to.
     *
     * @return The updated {@link stat.domain.Product Product}.
     */
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
    public LinkedHashSet<Product> searchForProducts(String name, String description, BigDecimal minPrice, BigDecimal maxPrice) {
        List searchResult = productRepo.findAll(productWithSpecification(name, description, minPrice, maxPrice));

        LinkedHashSet<Product> products = new LinkedHashSet<Product>();
        for (Object productObject : searchResult) {
            products.add(((Product) productObject));
        }
        return products;
    }

    public void deleteProduct(Integer productId) throws SoldProductDeletionException {
        LinkedHashSet<Sale> salesOfProduct = this.getSalesOfProduct(productId);
        if (salesOfProduct.size() != 0) {
            Sale sale = salesOfProduct.stream().findAny().get();
            throw new SoldProductDeletionException(productId, sale);
        }
        productRepo.delete(productId);
    }

    private Specification<Product> productWithSpecification(String name, String description, BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, builder) -> {
            ArrayList<Predicate> predicates = new ArrayList<Predicate>();

            if (name != null && !name.equals("")) {
                predicates.add(builder.like(builder.lower(root.get("name").as(String.class)), "%" + name.toLowerCase() + "%"));
            }

            if (description != null && !description.equals("")) {
                predicates.add(builder.like(builder.lower(root.get("description").as(String.class)), "%" + description.toLowerCase() + "%"));
            }

            if (minPrice != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("price").as(BigDecimal.class), minPrice));
            }

            if (maxPrice != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("price").as(BigDecimal.class), maxPrice));
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}

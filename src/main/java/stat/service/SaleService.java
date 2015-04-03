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
import stat.service.repository.SaleRepo;

import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SaleService {
    @Autowired
    private SaleRepo saleRepo;

    @Autowired
    private ProductService productService;

    public Sale createNewSale(String customerName) {
        return this.createNewSale(customerName, new Date());
    }

    public Sale createNewSale(String customerName, Date dateOfSale) {
        Sale sale = new Sale(customerName);
        sale.setDate(dateOfSale);
        return this.save(sale);
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

    public Sale addProduct(Sale sale, Integer productId, int amount) {
        Product product = productService.getProductWithId(productId);
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

    @Transactional(readOnly = true)
    public LinkedHashSet<String> getCustomerNames() {
        Set<Sale> allSales = this.getAllSales();
        return allSales.stream().map(Sale::getCustomerName).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Searches sales with the given criteria. If a {@code null} value is provided for any
     * criteria parameter, that criteria won't be included in the search. For example:
     * <pre>
     *   searchForSales(null, &lt;some date&gt;, null);</pre>
     * will return all sales with dates later than (and including) the given {@link
     * java.util.Date Date}. Another example would be that:
     * <pre>
     *   searchForSales("jack", null, &lt;some date&gt;);</pre>
     * will return all sales with customer names including the string 'jack' (e.g.
     * 'Michael Jackson', 'Jack Johnson') and with dates prior to (and including) the
     * given {@link java.util.Date Date}.
     *
     * @param customerName
     *         The customer name to limit the search to. Could be a substring of the name.
     *         Case-insensitive. If a {@code null} value is provided, the search won't be
     *         restricted to a specific customer name.
     * @param from
     *         Limits the date of the sale search to begin with the specified 'from' date.
     *         The specified date is also included (so a sale at the exact same date will
     *         also be returned). If a {@code null} value is provided, the search won't be
     *         restricted to a specific starting date.
     * @param until
     *         Limits the date of the sale search to end with the specified 'until' date.
     *         The specified date is also included (so a sale at the exact same date will
     *         also be returned). If a {@code null} value is provided, the search won't be
     *         restricted to a specific ending date.
     *
     * @return The {@link java.util.LinkedHashSet Set} of {@link stat.domain.Sale sales}
     * which match the given criteria.
     */
    @Transactional(readOnly = true)
    public LinkedHashSet<Sale> searchForSales(String customerName, Date from, Date until) {
        List searchResult = saleRepo.findAll(saleWithSpecification(customerName, from, until));

        LinkedHashSet<Sale> sales = new LinkedHashSet<Sale>();
        for (Object saleObject : searchResult) {
            sales.add(((Sale) saleObject));
        }
        return sales;
    }

    public void deleteSale(Sale sale) {
        saleRepo.delete(sale);
    }

    public void deleteSale(Integer saleId) {
        saleRepo.delete(saleId);
    }

    private Specification<Sale> saleWithSpecification(String customerName, Date from, Date until) {
        return (root, query, builder) -> {
            ArrayList<Predicate> predicates = new ArrayList<Predicate>();

            if (customerName != null && !customerName.equals("")) {
                predicates.add(builder.like(builder.lower(root.get("customerName").as(String.class)), "%" + customerName.toLowerCase() + "%"));
            }

            if (from != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("date").as(Date.class), from));
            }

            if (until != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("date").as(Date.class), until));
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}

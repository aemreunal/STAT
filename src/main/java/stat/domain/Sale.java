package stat.domain;

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


import java.util.*;
import javax.persistence.*;

//@SuppressWarnings("UnusedDeclaration")
@Entity
@Table(name = "sales")
public class Sale {
    // Empty constructor, required by Hibernate
    public Sale() {}

    @Id
    @Column(name = "sale_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @OrderColumn
    private Integer saleId;

    // Sale is the owner of the relationship
    @ManyToMany(targetEntity = Product.class,
            fetch = FetchType.LAZY)
    @JoinTable(name = "sales_to_products",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @OrderBy("productId")
    private Set<Product> products = new LinkedHashSet<Product>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "amounts", joinColumns=@JoinColumn(name="sale_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "amount")
    private Map<Integer, Integer> amounts = new LinkedHashMap<>();

    @Column(name = "date", nullable = false)
    private Date date = null;

    // Helper methods

    public void addProductToSale(Product product, int amount) {
        getProducts().add(product);
        getAmounts().put(product.getProductId(), amount);
    }

    public int getAmountSold(Product product) {
        return getAmounts().get(product.getProductId());
    }

    // Getters & Setters

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Map<Integer, Integer> getAmounts() {
        return amounts;
    }

    public void setAmounts(Map<Integer, Integer> amounts) {
        this.amounts = amounts;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @PrePersist
    private void setInitialProperties() {
        // Set sales date
        if (date == null) {
            setDate(new Date());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Sale) {
            Sale sale = (Sale) obj;
            return sale.getSaleId().intValue() == this.getSaleId().intValue();
        }
        return false;
    }
}

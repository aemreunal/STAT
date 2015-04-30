package stat.domain;

/*
 * ******************************* *
 * Copyright (c) 2015              *
 *                                 *
 * Sales Tracking & Analytics Tool *
 *                                 *
 * @author Ahmet Emre Ünal         *
 * @author Uğur Özkan              *
 * @author Burcu Başak Sarıkaya    *
 * @author Eray Tunçer             *
 *                                 *
 * ******************************* *
 */

import stat.config.GlobalSettings;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.Size;

//@SuppressWarnings("UnusedDeclaration")
@Entity
@Table(name = "sales")
public class Sale implements Comparable {
    public static final int CUSTOMER_NAME_MAX_LENGTH = 150;

    @Id
    @Column(name = "sale_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @OrderColumn
    private Integer saleId;

    @Column(name = "customer_name", nullable = false, length = CUSTOMER_NAME_MAX_LENGTH)
    @Size(min = 1, max = CUSTOMER_NAME_MAX_LENGTH)
    private String customerName = "";

    // Sale is the owner of the relationship
    @ManyToMany(targetEntity = Product.class,
            fetch = FetchType.LAZY)
    @JoinTable(name = "sales_to_products",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @OrderBy("productId")
    private Set<Product> products = new LinkedHashSet<>();

    // The amounts map from a product ID to the amount sold of that product
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "amounts", joinColumns = @JoinColumn(name = "sale_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "amount")
    private Map<Integer, Integer> amounts = new LinkedHashMap<>();

    @Column(name = "date", nullable = false)
    private Date date = null;

    @Column(name = "total_price", nullable = false, precision = GlobalSettings.PRICE_TOTAL_PRECISION, scale = GlobalSettings.PRICE_DECIMAL_PRECISION)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    // Empty constructor, required by Hibernate
    public Sale() {
    }

    public Sale(String customerName) {
        this.customerName = customerName;
    }

    // Helper methods

    public void addProductToSale(Product product, int amount) {
        getProducts().add(product);
        getAmounts().put(product.getProductId(), amount);
        addToPrice(product.getPrice().multiply(BigDecimal.valueOf(amount)));
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    private void addToPrice(BigDecimal value) {
        setTotalPrice(getTotalPrice().add(value));
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

    @Override
    public int compareTo(Object o) {
        if (o instanceof Sale) {
            int otherId = ((Sale) o).getSaleId();
            int thisId = this.getSaleId();
            if (otherId > thisId) {
                return -1;
            } else if (otherId < thisId) {
                return 1;
            }
        }
        return 0;
    }
}

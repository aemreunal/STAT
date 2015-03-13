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


import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
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
            joinColumns = @JoinColumn(name = "sale_Id"),
            inverseJoinColumns = @JoinColumn(name = "product_Id"))
    @OrderBy("productId")
    private Set<Product> products = new LinkedHashSet<Product>();

    @Column(name = "date", nullable = false)
    private Date date = null;

    // Helper methods

    public void addProductToSale(Product product, int amount) {
        getProducts().add(product);
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
}

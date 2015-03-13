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

import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.*;

//@SuppressWarnings("UnusedDeclaration")
@Entity
@Table(name = "products")
public class Product {
    // Empty constructor, required by Hibernate
    public Product() {}

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @OrderColumn
    private Integer productId;

    @ManyToMany(targetEntity = Sale.class,
            mappedBy = "products",
            fetch = FetchType.LAZY)
    @OrderBy("saleId")
    private Set<Sale> sales = new LinkedHashSet<Sale>();

    // Getters & Setters

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            Product product = (Product) obj;
            return product.getProductId().intValue() == this.getProductId().intValue();
        }
        return false;
    }
}

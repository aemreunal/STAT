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
import javax.validation.constraints.Size;

//@SuppressWarnings("UnusedDeclaration")
@Entity
@Table(name = "products")
public class Product {
    public static final int NAME_MAX_LENGTH        = 50;
    public static final int DESCRIPTION_MAX_LENGTH = 200;

    // Empty constructor, required by Hibernate
    public Product() {
    }

    public Product(String name) {
        this(name, "");
    }

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @OrderColumn
    private Integer productId;

    @Column(name = "name", nullable = false, unique = true, length = NAME_MAX_LENGTH)
    @Size(min = 1, max = NAME_MAX_LENGTH)
    private String name = "";

    @Column(name = "description", nullable = false, length = DESCRIPTION_MAX_LENGTH)
    @Size(max = DESCRIPTION_MAX_LENGTH)
    private String description = "";

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

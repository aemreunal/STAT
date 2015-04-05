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
 * @author Eray Tuncer             *
 *                                 *
 * ******************************* *
 */

import stat.config.GlobalSettings;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Size;

//@SuppressWarnings("UnusedDeclaration")
@Entity
@Table(name = "products")
public class Product implements Comparable {
    public static final int NAME_MAX_LENGTH        = 50;
    public static final int DESCRIPTION_MAX_LENGTH = 200;

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

    @Column(name = "price", nullable = false, precision = GlobalSettings.PRICE_TOTAL_PRECISION, scale = GlobalSettings.PRICE_DECIMAL_PRECISION)
    private BigDecimal price;

    @ManyToMany(targetEntity = Sale.class,
            mappedBy = "products",
            fetch = FetchType.LAZY)
    @OrderBy("saleId")
    private Set<Sale> sales = new LinkedHashSet<Sale>();

    // Empty constructor, required by Hibernate
    public Product() {
    }

    public Product(String name, BigDecimal price) {
        this(name, "", price);
    }

    public Product(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = roundPrice(price);
    }

    // Helpers

    private BigDecimal roundPrice(BigDecimal price) {
        return price.setScale(GlobalSettings.PRICE_DECIMAL_PRECISION, BigDecimal.ROUND_HALF_UP);
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = roundPrice(price);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            Product product = (Product) obj;
            return product.getProductId().intValue() == this.getProductId().intValue();
        }
        return false;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Product) {
            int otherId = ((Product) o).getProductId();
            int thisId = this.getProductId();
            if (otherId > thisId) {
                return -1;
            } else if (otherId < thisId) {
                return 1;
            }
        }
        return 0;
    }
}

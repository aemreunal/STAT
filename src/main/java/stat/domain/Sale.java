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

@Entity
@Table(name = "sales")
//@Access(AccessType.PROPERTY)
public class Sale {
    // Empty constructor, required by Hibernate
    public Sale() {}

    @Id
    @Column(name = "sale_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @OrderColumn
    private Integer saleId;

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    // Sale is the owner of the relationship
    @ManyToMany(targetEntity = Product.class,
            fetch = FetchType.LAZY)
    @JoinTable(name = "sales_to_products",
            joinColumns = @JoinColumn(name = "sale_Id"),
            inverseJoinColumns = @JoinColumn(name = "product_Id"))
    @OrderBy("productId")
    private Set<Product> products = new LinkedHashSet<Product>();

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void addProductToSale(Product product, int amount) {
        getProducts().add(product);
    }
}

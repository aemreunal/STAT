package stat.service.repository;

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

import java.util.Set;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepo extends PagingAndSortingRepository<Product, Integer>, JpaSpecificationExecutor {

    Set<Product> findAll();

    Set<Product> findAll(Sort sort);

    /**
     * Matches the name exactly
     *
     * @param name
     *         The name of the {@link stat.domain.Product Product}.
     *
     * @return A {@link java.util.Set Set}<{@link stat.domain.Product Product}> containing
     * the products with the matched name.
     */
    Set<Product> findByNameLike(String name);

    Set<Product> findByNameContaining(String name);

    Set<Product> findByDescriptionContaining(String description);

}